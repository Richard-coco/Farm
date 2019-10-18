package cn.farm.enums;

public enum UserModifyEnum {
    SUCCESS(200,"ok"),
    INNER_ERROR(404,"undefined");

    private Integer state;
    private String stateInfo;

    private UserModifyEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static UserModifyEnum stateOf(int index) {
        for (UserModifyEnum state : values()) {
            if (state.getState() == index) {
                return state;
            }
        }
        return null;
    }
}
