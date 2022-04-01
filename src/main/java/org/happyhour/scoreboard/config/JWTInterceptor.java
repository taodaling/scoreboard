package org.happyhour.scoreboard.config;

import org.happyhour.scoreboard.service.UserService;
import org.happyhour.scoreboard.util.Context;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JWTInterceptor implements AsyncHandlerInterceptor {
    @Resource
    UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String jwt = request.getHeader("jwt");
        if (!StringUtils.isEmpty(jwt)) {
            Integer user = userService.parseJwt(jwt);
            Context.get().setUserId(user);
        }
        return true;
    }

}
