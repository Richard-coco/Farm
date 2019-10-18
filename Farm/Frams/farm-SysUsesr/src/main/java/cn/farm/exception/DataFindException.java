package cn.farm.exception;

public class DataFindException extends RuntimeException {
    public DataFindException(String code, String message) {
    }

    @Override
    public String getMessage() {
        return "数据库中不存在该数据,查询失败";
    }

    public String getCode() {
        return "603";
    }
}
