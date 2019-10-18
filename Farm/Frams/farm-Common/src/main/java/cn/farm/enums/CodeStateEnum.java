package cn.farm.enums;

public enum CodeStateEnum {
    SUCCESS(200, "ok"),
    DEFAULT(603, "数据库中不存在该数据,查询失败"),
    SEND_NULL(500,"server error"),
    NO_PHONE(1001,"该号码未被绑定，请先注册"),//该号码未被绑定，请先注册
    USED_PHONE(1004,"该号码已被使用，请重新输入"),//该号码已被使用，请重新输入
    INNER_ERROR(404,"undefined"),//
    FAILURE(1002,"验证码错误"),
    VICTORY(200,"ok"),
    EQUAL_PHONE(1005,"电话号码,前后一致，请使用新的电话号码");//电话号码,前后一致，请使用新的电话号码

    private Integer state;
    private String stateInfo;

    private CodeStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static CodeStateEnum stateOf(int index) {
        for (CodeStateEnum state : values()) {
            if (state.getState() == index) {
                return state;
            }
        }
        return null;
    }
}
