package org.happyhour.scoreboard.mapper;

import org.happyhour.scoreboard.model.Match;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

/**
 * (Match)数据Mapper
 *
 * @author dalingtao
 * @since 2022-03-31 21:27:39
 * @description 
*/
@Mapper
public interface MatchMapper extends BaseMapper<Match> {

    void insertMatch(Match match);

    List<Match> selectNewestMatch();

    List<Match> selectMatchByStartTimeAndEndTime(String startTime, String endTime);
}
