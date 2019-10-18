package cn.farm.exception;

public class DefaultException extends RuntimeException {
    public DefaultException(String message) {
        super(message);
    }

    public DefaultException(String message, Throwable cause) {
        super(message, cause);
    }
}
