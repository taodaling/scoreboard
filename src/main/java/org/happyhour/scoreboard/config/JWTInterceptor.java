package org.happyhour.scoreboard.config;

import org.happyhour.scoreboard.service.UserService;
import org.happyhour.scoreboard.util.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JWTInterceptor implements AsyncHandlerInterceptor {
    static Logger logger = LoggerFactory.getLogger(JWTInterceptor.class);

    @Resource
    UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String jwt = request.getHeader("jwt");
        if (!StringUtils.isEmpty(jwt)) {
            try {
                Integer user = userService.parseJwt(jwt);
                Context.get().setUserId(user);
            } catch (Exception e) {
                //can't parse
                logger.info("can't parse jwt", e);
            }
        }
        return true;
    }

}
