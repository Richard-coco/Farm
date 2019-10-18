package cn.farm.exception;

public class FileException extends RuntimeException {
    public FileException(String code, String message) {
    }

    @Override
    public String getMessage() {
        return "插入文件不存在";
    }

    public String getCode() {
        return "505";
    }
}
