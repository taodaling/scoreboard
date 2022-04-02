package org.happyhour.scoreboard.service;

import org.happyhour.scoreboard.controller.requestBody.AddMatchRequestBody;
import org.happyhour.scoreboard.enums.ScoreEnum;
import org.happyhour.scoreboard.mapper.MatchMapper;
import org.happyhour.scoreboard.mapper.UserMapper;
import org.happyhour.scoreboard.mapper.UsermatchMapper;
import org.happyhour.scoreboard.model.AddMatchRequestModel;
import org.happyhour.scoreboard.model.GetUserMatchModel;
import org.happyhour.scoreboard.model.Match;
import org.happyhour.scoreboard.model.RankModel;
import org.happyhour.scoreboard.model.User;
import org.happyhour.scoreboard.model.Usermatch;
import org.happyhour.scoreboard.util.BizException;
import org.happyhour.scoreboard.util.Context;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class MatchService {

    @Resource
    UserMapper userMapper;
    @Resource
    MatchMapper matchMapper;
    @Resource
    UsermatchMapper usermatchMapper;

    @Resource
    UserService userService;

    public void addMatch(AddMatchRequestBody addMatchRequestBody) {
        Date now = new Date();
        //only admin
        Context.assertIsAdmin();

        StringBuilder attendancesSb = new StringBuilder();
        for (AddMatchRequestModel model : addMatchRequestBody.getAddMatchRequestModels()) {
            attendancesSb.append(model.getUserId()).append("|");
        }
        attendancesSb.deleteCharAt(attendancesSb.length() - 1);

        Match match = new Match();
        match.setMatchId(null);
        match.setMatchTime(now);
        match.setAttendances(attendancesSb.toString());
        matchMapper.insertMatch(match);

        match = matchMapper.selectNewestMatch().get(0);

        for (AddMatchRequestModel model : addMatchRequestBody.getAddMatchRequestModels()) {
            Usermatch usermatch = new Usermatch();
            usermatch.setId(null);
            usermatch.setMatchId(match.getMatchId());
            usermatch.setUserId(model.getUserId());
            usermatch.setRole(model.getRole());
            usermatch.setScore(model.getScore());
            usermatchMapper.insertUsermatch(usermatch);
        }
    }

    public List<RankModel> getRank() {
        List<User> allUsers = userService.getAllUser();
        List<RankModel> result = new ArrayList<RankModel>();

        for (User user : allUsers) {
            RankModel rankModel = new RankModel();
            rankModel.setUserId(user.getUserid());

            HashMap<String, Object> conditionMap = new HashMap<String, Object>();
            conditionMap.put("userId", user.getUserid());
            List<Usermatch> usermatches = usermatchMapper.selectByMap(conditionMap);

            rankModel.setTotalScore(CollectionUtils.isEmpty(usermatches) ? 0 : usermatches.stream().mapToInt(Usermatch::getScore).sum());

            result.add(rankModel);
        }

        result.sort(new Comparator<RankModel>() {
            @Override
            public int compare(RankModel o1, RankModel o2) {
                return o2.getTotalScore() - o1.getTotalScore();
            }
        });
        return result;
    }

    public List<GetUserMatchModel> getUserMatch(Date startTime, Date endTime) {
        BizException.assertTrue(startTime != null, "startTime can't be null");
        BizException.assertTrue(endTime != null, "endTime can't be null");
        List<GetUserMatchModel> result = new ArrayList<>();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        endTime = addOneDay(endTime);
        List<Match> matches = matchMapper.selectMatchByStartTimeAndEndTime(formatter.format(startTime), formatter.format(endTime));
        List<User> allUsers = userService.getAllUser();

        for (User user : allUsers) {
            GetUserMatchModel getUserMatchModel = new GetUserMatchModel();
            getUserMatchModel.setUserId(user.getUserid());
            List<Usermatch> resultUserMatches = new ArrayList<>();

            for (Match match : matches) {
                List<Usermatch> usermatches = usermatchMapper.selectUsermatchByUserIdAndMatchId(user.getUserid(), match.getMatchId());
                if (CollectionUtils.isEmpty(usermatches)) {
                    Usermatch absentUsermatch = new Usermatch();
                    absentUsermatch.setId(null);
                    absentUsermatch.setUserId(user.getUserid());
                    absentUsermatch.setMatchId(match.getMatchId());
                    absentUsermatch.setScore(ScoreEnum.ABSENT.id);
                    absentUsermatch.setRole(null);

                    resultUserMatches.add(absentUsermatch);
                    usermatchMapper.insertUsermatch(absentUsermatch);
                } else {
                    resultUserMatches.add(usermatches.get(0));
                }
            }
            getUserMatchModel.setUsermatches(resultUserMatches);
            result.add(getUserMatchModel);
        }
        return result;
    }

    private Date addOneDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, 1);
        return c.getTime();
    }
}
