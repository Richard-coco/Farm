package cn.farm.exception;

public class LikeException extends RuntimeException {
    public LikeException(String message) {
        super(message);
    }

    public LikeException(String message, Throwable cause) {
        super(message, cause);
    }
}
