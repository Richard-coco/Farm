package cn.farm.exception;

/**
 * @program: Farms
 * @description: 数据操作异常
 * @author: Mr.zuo
 * @create: 2019-10-10 15:08
 **/

public class DataException extends RuntimeException {
    public DataException(String code, String message) {
    }

    @Override
    public String getMessage() {
        return "服务端数据操作错误";
    }

    public String getCode() {
        return "409";
    }
}
