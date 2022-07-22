package org.happyhour.scoreboard.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import de.gesundkrank.jskills.Rating;
import de.gesundkrank.jskills.TrueSkillCalculator;
import org.happyhour.scoreboard.algo.TrueSkillAlgorithm;
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
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    @Resource
    private Function<int[], Integer> scoreAlgorithm;
    @Resource
    private Function<int[], Integer> inverseRatingAlgorithm;
    @Resource
    private TrueSkillAlgorithm trueSkillAlgorithm;

    public List<RankModel> getRank() {
        List<User> allUsers = userService.getAllUser();
        List<RankModel> result = new ArrayList<RankModel>();
        List<Usermatch> allMatch = usermatchMapper.selectList(new QueryWrapper<>());
        Map<Integer, List<Usermatch>> groupMatchByMatchId = allMatch.stream()
                .collect(Collectors.groupingBy(x -> x.getMatchId()));
        Map<Integer, List<Usermatch>> groupMatchByUserId = allMatch.stream()
                .collect(Collectors.groupingBy(x -> x.getUserId()));
        //Map<Integer, Double> ratings = trueSkillAlgorithm.calcRating(new TreeMap<>(groupMatchByMatchId).values());


        for (User user : allUsers) {
            RankModel rankModel = new RankModel();
            rankModel.setUserId(user.getUserid());

//            HashMap<String, Object> conditionMap = new HashMap<String, Object>();
//            conditionMap.put("userId", user.getUserid());
            List<Usermatch> usermatches = groupMatchByUserId.getOrDefault(user.getUserid(), Collections.emptyList());//usermatchMapper.selectByMap(conditionMap);
            //newest first
            usermatches.sort(Comparator.comparingInt(match -> -match.getMatchId()));
            int[] scores = usermatches.stream().mapToInt(x -> x.getScore()).toArray();

            //Exclude users have no competition records
            if(!Arrays.stream(scores).filter(x -> x != 0).findAny().isPresent()) {
                continue;
            }

            rankModel.setTotalScore(scoreAlgorithm.apply(scores));
            rankModel.setRating(inverseRatingAlgorithm.apply(scores));
            //rankModel.setTrueSkill((int)Math.round(ratings.getOrDefault(user.getUserid(), 0D)));
            result.add(rankModel);
        }

        result.sort(Comparator.comparing(RankModel::getRating).reversed());
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
