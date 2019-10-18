package cn.farm.exception;

public class CodeException extends RuntimeException {
    public CodeException(String message) {
        super(message);
    }

    public CodeException(String message, Throwable cause) {
        super(message, cause);
    }
}
