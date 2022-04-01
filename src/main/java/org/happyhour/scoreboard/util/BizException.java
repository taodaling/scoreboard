package org.happyhour.scoreboard.util;

public class BizException extends RuntimeException{
    public BizException() {
        super();
    }

    public BizException(String message) {
        super(message);
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
    }

    public BizException(Throwable cause) {
        super(cause);
    }

    public static void assertTrue(boolean flag, String msg) {
        if(!flag) {
            throw new BizException(msg);
        }
    }
}
