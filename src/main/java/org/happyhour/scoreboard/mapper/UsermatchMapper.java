package org.happyhour.scoreboard.mapper;

import org.happyhour.scoreboard.model.Match;
import org.happyhour.scoreboard.model.Usermatch;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

/**
 * (UserMatch)数据Mapper
 *
 * @author dalingtao
 * @description
 * @since 2022-03-31 21:27:39
 */
@Mapper
public interface UsermatchMapper extends BaseMapper<Usermatch> {

    void insertUsermatch(Usermatch usermatch);

    List<Usermatch> selectUsermatchByUserId(Integer userId);

    List<Usermatch> selectUsermatchByMatchId(Integer matchId);

    List<Usermatch> selectUsermatchByUserIdAndMatchId(Integer userId, Integer matchId);

}
