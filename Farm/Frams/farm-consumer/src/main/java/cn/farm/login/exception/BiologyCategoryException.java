package cn.farm.login.exception;

public class BiologyCategoryException extends RuntimeException {
    public BiologyCategoryException(String message) {
        super(message);
    }

    public BiologyCategoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
