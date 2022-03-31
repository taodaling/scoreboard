package org.happyhour.scoreboard.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import org.happyhour.scoreboard.mapper.UserMapper;
import org.happyhour.scoreboard.model.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController("/")
public class DBController {
    @Resource
    UserMapper um;

    @RequestMapping("/listUser")
    List<User> listUser() {
        QueryWrapper<User> ws = new QueryWrapper<>();
        return um.selectList(ws);
    }
}
