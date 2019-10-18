package cn.farm.enums;

public enum SessionStateEnum {

    SUCCESS(200,"ok"),
    DEFAULT(1003,"session已过期或未登录"),
    INNER_ERROR(404,"undefined");

    private Integer state;
    private String stateInfo;

    private SessionStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static SessionStateEnum stateOf(int index) {
        for (SessionStateEnum state : values()) {
            if (state.getState() == index) {
                return state;
            }
        }
        return null;
    }
}
