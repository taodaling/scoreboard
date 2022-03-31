package org.happyhour.scoreboard.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import org.happyhour.scoreboard.mapper.UserMapper;
import org.happyhour.scoreboard.model.User;
import org.happyhour.scoreboard.util.BizException;
import org.happyhour.scoreboard.util.Context;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class UserService {
    @Resource
    UserMapper userMapper;

    public User getUser(Integer id) {
        return userMapper.selectById(id);
    }

    public List<User> getAllUser() {
        return userMapper.selectList(new QueryWrapper<>());
    }

    public void updateUser(User user) {
        BizException.assertTrue(Objects.equals(Context.getNotNullUser(), user.getUserid()), "lack of permission");
        user.setPassword(encryptPassword(user.getPassword()));
        userMapper.updateById(user);
    }

    public String login(User user) {
        BizException.assertTrue(user.getAlias() != null, "alias can't be null");
        BizException.assertTrue(user.getPassword() != null, "password can't be null");
        user.setPassword(encryptPassword(user.getPassword()));
        User exist = userMapper.selectOne(new QueryWrapper<>(user));
        BizException.assertTrue(exist != null, "user doesn't exist");
        return jwt(exist);
    }

    @Value("${config.salt}")
    String salt;

    String encryptPassword(String s) {
        return DigestUtils.md5DigestAsHex((s + salt).getBytes());
    }

    @Resource
    Gson gson;

    public String jwt(User user) {
        return JWT.create().withExpiresAt(new Date(System.currentTimeMillis() + 12 * 3600 * 1000L))
                .withIssuedAt(new Date())
                .withClaim("userId", user.getUserid())
                .sign(Algorithm.none());
    }

    public Integer parseJwt(String jwt) {
        DecodedJWT decodedJWT = JWT.decode(jwt);
        return decodedJWT.getClaim("userId").asInt();
    }

    public void addUser(User user) {
        user.setUserid(null);
        user.setPassword(encryptPassword(user.getPassword()));
        userMapper.insert(user);
    }
}
