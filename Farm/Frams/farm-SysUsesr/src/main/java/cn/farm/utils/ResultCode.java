package cn.farm.utils;
/**
 * 枚举了一些常用API操作码
 * Created by macro on 2019/4/19.
 */
public enum ResultCode implements IErrorCode {
    SUCCESS(200, "ok"),
    FAILED(500, "server error"),
    VALIDATE_FAILED(404, "undefined"),
    UNAUTHORIZED(401, "unauthorized"),
    FORBIDDEN(403, "forbidden");
    private long state;
    private String stateInfo;

    private ResultCode(long state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    @Override
    public long getState() {
        return state;
    }

    @Override
    public String getMessage() {
        return stateInfo;
    }
}

