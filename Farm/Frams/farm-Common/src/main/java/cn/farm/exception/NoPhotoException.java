package cn.farm.exception;

public class NoPhotoException extends RuntimeException {
    public NoPhotoException(String message) {
        super(message);
    }

    public NoPhotoException(String message, Throwable cause) {
        super(message, cause);
    }
}
