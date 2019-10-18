package cn.farm.login.enums;

public enum ProtectStateEnum {
    SUCCESS(1057, "查询成功"),
    DEFAULT(1058, "发送失败，请重试"),
    FOUND_NULL(1059,"未找到任何相关信息"),
    PHOTO_NULL(1060,"图片不能空"),
    PYTHON_ERROR(1061,"未检索到任何相关信息，请重新上传图片"),
    INNER_ERROR(1062,"系统错误，请重新发送");

    private Integer state;
    private String stateInfo;

    private ProtectStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static ProtectStateEnum stateOf(int index) {
        for (ProtectStateEnum state : values()) {
            if (state.getState() == index) {
                return state;
            }
        }
        return null;
    }
}
