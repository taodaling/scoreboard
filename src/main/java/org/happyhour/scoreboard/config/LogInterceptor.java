package org.happyhour.scoreboard.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.ConcurrentHashMap;

public class LogInterceptor implements AsyncHandlerInterceptor {
    static Logger logger = LoggerFactory.getLogger(LogInterceptor.class);
    ConcurrentHashMap<HttpServletRequest, Long> timeRegistry = new ConcurrentHashMap<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        timeRegistry.put(request, System.currentTimeMillis());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        Long past = timeRegistry.remove(request);
        logger.info("finish request {} in {} ms", request.getRequestURL(), System.currentTimeMillis() - past);
    }
}
