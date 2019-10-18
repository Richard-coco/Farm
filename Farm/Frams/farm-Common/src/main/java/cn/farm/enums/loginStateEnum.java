package cn.farm.enums;

public enum loginStateEnum {

    SUCCESS(200, "ok"),
    DEFAULT(1000, "用户名或密码错误"),
    INNER_ERROR(404,"undefined");

    private Integer state;
    private String stateInfo;

    private loginStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static loginStateEnum stateOf(int index) {
        for (loginStateEnum state : values()) {
            if (state.getState() == index) {
                return state;
            }
        }
        return null;
    }
}