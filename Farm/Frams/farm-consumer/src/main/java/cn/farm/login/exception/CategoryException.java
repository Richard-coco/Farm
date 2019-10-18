package cn.farm.login.exception;

public class CategoryException extends RuntimeException {
    public CategoryException(String message) {
        super(message);
    }

    public CategoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
