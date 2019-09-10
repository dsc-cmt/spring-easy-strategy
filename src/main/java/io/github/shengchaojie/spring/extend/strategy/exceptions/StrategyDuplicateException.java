package io.github.shengchaojie.spring.extend.strategy.exceptions;

/**
 * @author shengchaojie
 * @date 2019-09-10
 **/
public class StrategyDuplicateException extends RuntimeException{

    public StrategyDuplicateException() {
    }

    public StrategyDuplicateException(String message) {
        super(message);
    }

    public StrategyDuplicateException(String message, Throwable cause) {
        super(message, cause);
    }

    public StrategyDuplicateException(Throwable cause) {
        super(cause);
    }

    public StrategyDuplicateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
