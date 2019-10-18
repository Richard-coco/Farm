package cn.farm.enums;

public enum MessageStateEnum {
    SUCCESS(200, "ok"),
    DEFAULT(500, "server error"),
    FOUND_NULL(1006,"未找到任何相关信息"),
    PHOTO_NULL(500,"server error"),
    PYTHON_ERROR(1006,"未找到任何相关信息"),
    INNER_ERROR(404,"undefined");

    private Integer state;
    private String stateInfo;

    private MessageStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static MessageStateEnum stateOf(int index) {
        for (MessageStateEnum state : values()) {
            if (state.getState() == index) {
                return state;
            }
        }
        return null;
    }
}
