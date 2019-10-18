package cn.farm.login.enums;

public enum MessageStateEnum {
    SUCCESS(1036, "查询成功"),
    DEFAULT(1037, "发送失败，请重试"),
    FOUND_NULL(1038,"未找到任何相关信息"),
    PHOTO_NULL(1039,"图片不能空"),
    PYTHON_ERROR(1040,"未检索到任何相关信息，请重新上传图片"),
    INNER_ERROR(1041,"系统错误，请重新发送");

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
