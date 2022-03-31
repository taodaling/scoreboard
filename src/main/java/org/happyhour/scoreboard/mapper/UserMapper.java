package org.happyhour.scoreboard.mapper;

import org.happyhour.scoreboard.model.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * (User)数据Mapper
 *
 * @author dalingtao
 * @since 2022-03-31 21:27:39
 * @description 
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
