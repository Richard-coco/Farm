package cn.farm.exception;

public class BiologyCategoryException extends RuntimeException {
    public BiologyCategoryException(String message) {
        super(message);
    }

    public BiologyCategoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
