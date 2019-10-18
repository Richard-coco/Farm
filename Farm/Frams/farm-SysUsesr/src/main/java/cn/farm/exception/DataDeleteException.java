package cn.farm.exception;

public class DataDeleteException extends RuntimeException {
    public DataDeleteException(String code, String message) {
    }

    @Override
    public String getMessage() {
        return "数据库中不存在该数据,删除失败";
    }

    public String getCode() {
        return "602";
    }
}
