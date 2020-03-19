package com.cmt.springstrategy.exceptions;

/**
 * @author shengchaojie
 * @date 2019-09-10
 **/
public class StrategyException extends RuntimeException{

    public StrategyException() {
    }

    public StrategyException(String message) {
        super(message);
    }

    public StrategyException(String message, Throwable cause) {
        super(message, cause);
    }

    public StrategyException(Throwable cause) {
        super(cause);
    }

    public StrategyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
