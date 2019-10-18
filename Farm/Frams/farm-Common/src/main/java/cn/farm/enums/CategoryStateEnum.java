package cn.farm.enums;

public enum CategoryStateEnum {
    SUCCESS(200, "ok"),
    DEFAULT(1006, "未找到任何相关信息"),
    INNER_ERROR(404,"undefined");
    private Integer state;
    private String stateInfo;

    private CategoryStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static CategoryStateEnum stateOf(int index) {
        for (CategoryStateEnum state : values()) {
            if (state.getState() == index) {
                return state;
            }
        }
        return null;
    }
}
