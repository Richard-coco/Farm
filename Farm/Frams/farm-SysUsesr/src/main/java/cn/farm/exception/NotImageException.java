package cn.farm.exception;

public class NotImageException extends Exception {
    public NotImageException(String code, String message) {

    }

    @Override
    public String getMessage() {
        return "插入的不是图片";
    }

    public String getCode() {
        return "503";
    }
}
