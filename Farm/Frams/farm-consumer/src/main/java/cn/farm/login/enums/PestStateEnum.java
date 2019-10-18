package cn.farm.login.enums;

public enum PestStateEnum {
    SUCCESS(1045, "查询成功"),
    DEFAULT(1046, "发送失败，请重试"),
    FOUND_NULL(1047,"未找到任何相关信息"),
    PHOTO_NULL(1048,"图片不能空"),
    PYTHON_ERROR(1049,"未检索到任何相关信息，请重新上传图片"),
    INNER_ERROR(1050,"系统错误，请重新发送");

    private Integer state;
    private String stateInfo;

    private PestStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static PestStateEnum stateOf(int index) {
        for (PestStateEnum state : values()) {
            if (state.getState() == index) {
                return state;
            }
        }
        return null;
    }
}
