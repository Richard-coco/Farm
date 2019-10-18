package cn.farm.enums;

public enum PhoneStateEnum {
    SUCCESS(200, "ok"),
    DEFAULT(500, "server error"),
    INNER_ERROR(404,"undefined");

    private Integer state;
    private String stateInfo;

    private PhoneStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static PhoneStateEnum stateOf(int index) {
        for (PhoneStateEnum state : values()) {
            if (state.getState() == index) {
                return state;
            }
        }
        return null;
    }
}
