package cn.farm.enums;

public enum PasswordStateEnum {
    SUCCESS(200, "ok"),
    DEFAULT(1003, "session已过期或未登录"),
    INNER_ERROR(404,"undefined");

    private Integer state;
    private String stateInfo;

    private PasswordStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static PasswordStateEnum stateOf(int index) {
        for (PasswordStateEnum state : values()) {
            if (state.getState() == index) {
                return state;
            }
        }
        return null;
    }
}
