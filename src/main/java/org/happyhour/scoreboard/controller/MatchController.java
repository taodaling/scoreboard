package org.happyhour.scoreboard.controller;

import org.happyhour.scoreboard.controller.requestBody.AddMatchRequestBody;
import org.happyhour.scoreboard.model.GetUserMatchModel;
import org.happyhour.scoreboard.model.RankModel;
import org.happyhour.scoreboard.service.MatchService;
import org.happyhour.scoreboard.util.Result;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class MatchController {

    @Resource
    MatchService matchService;

    @RequestMapping(value = "/match", method = RequestMethod.POST)
    public Result<Void> addMatch(@RequestBody AddMatchRequestBody addMatchRequestBody) {
        matchService.addMatch(addMatchRequestBody);
        return Result.ofSuccess();
    }

    @RequestMapping(value = "/rank", method = RequestMethod.GET)
    public Result<List<RankModel>> getRank() {
        return Result.ofSuccess(matchService.getRank());
    }

    @RequestMapping(value = "/user/match", method = RequestMethod.GET)
    public Result<List<GetUserMatchModel>> getUserMatch(@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date startTime, @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime) {
        return Result.ofSuccess(matchService.getUserMatch(startTime, endTime));
    }
}
