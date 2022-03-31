package org.happyhour.scoreboard.config;

import org.happyhour.scoreboard.util.BizException;
import org.happyhour.scoreboard.util.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionInterceptor {
    static Logger logger = LoggerFactory.getLogger(ExceptionInterceptor.class);

    @ExceptionHandler(BizException.class)
    public Result<String> handleBizException(BizException e) {
        logger.info("biz", e);
        return Result.ofFail(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Result<String> handleException(Exception e) {
        logger.error("catch unhandled exception", e);
        return Result.ofFail("server error");
    }
}
