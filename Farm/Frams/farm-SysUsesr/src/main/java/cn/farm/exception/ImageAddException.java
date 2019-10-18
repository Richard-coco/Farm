package cn.farm.exception;

public class ImageAddException extends RuntimeException {
    public ImageAddException(String code, String message) {

    }

    @Override
    public String getMessage() {
        return "image表插入异常";
    }

    public String getCode() {
        return "504";
    }

}
