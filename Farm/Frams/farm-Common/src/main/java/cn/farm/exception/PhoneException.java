package cn.farm.exception;

public class PhoneException extends RuntimeException {
    public PhoneException(String message) {
        super(message);
    }

    public PhoneException(String message, Throwable cause) {
        super(message, cause);
    }
}
