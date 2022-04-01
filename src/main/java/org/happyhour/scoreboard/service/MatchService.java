package org.happyhour.scoreboard.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.happyhour.scoreboard.controller.requestBody.AddMatchRequestBody;
import org.happyhour.scoreboard.enums.RoleEnum;
import org.happyhour.scoreboard.enums.ScoreEnum;
import org.happyhour.scoreboard.mapper.MatchMapper;
import org.happyhour.scoreboard.mapper.UserMapper;
import org.happyhour.scoreboard.mapper.UsermatchMapper;
import org.happyhour.scoreboard.model.*;
import org.happyhour.scoreboard.util.BizException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

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
        StringBuilder attendancesSb = new StringBuilder();
        for (AddMatchRequestModel model : addMatchRequestBody.getAddMatchRequestModels()) {
            attendancesSb.append(model.getUserId()).append("|");
        }
        attendancesSb.deleteCharAt(attendancesSb.length() - 1);

        Match match = new Match();
        match.setMatchId(null);
        match.setMatchTime(addMatchRequestBody.getTime());
        match.setAttendances(attendancesSb.toString());
        matchMapper.insertMatch(match);

        match = matchMapper.selectMatchByTime(addMatchRequestBody.getTime()).get(0);

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
                    absentUsermatch.setRole(RoleEnum.ABSENT.id);

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
}
