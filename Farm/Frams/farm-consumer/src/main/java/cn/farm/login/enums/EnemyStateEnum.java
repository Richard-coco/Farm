package cn.farm.login.enums;

public enum  EnemyStateEnum {
    SUCCESS(1024, "查询成功"),
    DEFAULT(1025, "发送失败，请重试"),
    FOUND_NULL(1026,"未找到任何相关信息"),
    PHOTO_NULL(1027,"图片不能空"),
    PYTHON_ERROR(1028,"未检索到任何相关信息，请重新上传图片"),
    INNER_ERROR(1029,"系统错误，请重新发送");

    private Integer state;
    private String stateInfo;

    private EnemyStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static EnemyStateEnum stateOf(int index) {
        for (EnemyStateEnum state : values()) {
            if (state.getState() == index) {
                return state;
            }
        }
        return null;
    }
}
