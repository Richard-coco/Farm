package cn.farm.exception;

public class PythonException extends RuntimeException {
    public PythonException(String message) {
        super(message);
    }

    public PythonException(String message, Throwable cause) {
        super(message, cause);
    }
}
