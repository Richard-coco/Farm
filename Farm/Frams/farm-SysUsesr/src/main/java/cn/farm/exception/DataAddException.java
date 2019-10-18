package cn.farm.exception;

public class DataAddException extends RuntimeException {
    public DataAddException(String code, String message) {
    }

    @Override
    public String getMessage() {
        return "数据库中存在该数据,请勿重新添加";
    }

    public String getCode() {
        return "601";
    }
}
