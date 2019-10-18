package cn.farm.login.enums;

public enum AddressStateEnum {
    SUCCESS(200, "ok"),
    DEFAULT(500, "server error");

    private Integer state;
    private String stateInfo;

    private AddressStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static AddressStateEnum stateOf(int index) {
        for (AddressStateEnum state : values()) {
            if (state.getState() == index) {
                return state;
            }
        }
        return null;
    }
}
