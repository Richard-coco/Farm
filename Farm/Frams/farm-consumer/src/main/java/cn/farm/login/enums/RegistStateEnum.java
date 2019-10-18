package cn.farm.login.enums;

public enum  RegistStateEnum {
    SUCCESS(200,"ok"),
    INNER_ERROR(404,"undefined"),
    DEFAULT(1004,"该号码已被使用，请重新输入"),
    EXPERT_SUCCESS(200,"ok");//我司工作人员将会再1-7个工作日内与您取得联系，请保持电话通畅，谢谢配合！

    private Integer state;
    private String stateInfo;

    private RegistStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static RegistStateEnum stateOf(int index) {
        for (RegistStateEnum state : values()) {
            if (state.getState() == index) {
                return state;
            }
        }
        return null;
    }
}
