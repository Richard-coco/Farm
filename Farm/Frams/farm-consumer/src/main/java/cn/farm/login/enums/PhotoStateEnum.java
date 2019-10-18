package cn.farm.login.enums;

public enum  PhotoStateEnum {
    SUCCESS(200, "ok"),
    DEFAULT(500, "server error"),
    INNER_ERROR(404,"undefined");

    private Integer state;
    private String stateInfo;

    private PhotoStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static PhotoStateEnum stateOf(int index) {
        for (PhotoStateEnum state : values()) {
            if (state.getState() == index) {
                return state;
            }
        }
        return null;
    }
}
