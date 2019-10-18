package cn.farm.exception;

public class ModifyException extends RuntimeException {
    public ModifyException(String message) {
        super(message);
    }

    public ModifyException(String message, Throwable cause) {
        super(message, cause);
    }
}
