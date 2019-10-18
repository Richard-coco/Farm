package cn.farm.login.enums;

public enum DiseaseStateEnum {
    SUCCESS(1018, "查询成功"),
    DEFAULT(1019, "发送失败，请重试"),
    FOUND_NULL(1020,"未找到任何相关信息"),
    PHOTO_NULL(1021,"图片不能空"),
    PYTHON_ERROR(1022,"未检索到任何相关信息，请重新上传图片"),
    INNER_ERROR(1023,"系统错误，请重新发送");

    private Integer state;
    private String stateInfo;

    private DiseaseStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static DiseaseStateEnum stateOf(int index) {
        for (DiseaseStateEnum state : values()) {
            if (state.getState() == index) {
                return state;
            }
        }
        return null;
    }
}
