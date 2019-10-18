package cn.farm.exception;

public class DataUpdateException extends RuntimeException {
    public DataUpdateException(String code, String message) {
    }

    @Override
    public String getMessage() {
        return "数据库中存在该数据,更新失败";
    }

    public String getCode() {
        return "604";
    }
}
