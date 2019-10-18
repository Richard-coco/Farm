package cn.farm.login.dto;

import cn.farm.login.enums.PasswordStateEnum;

public class PasswordExecution {
    private Integer state;
    private String stateInfo;
    public PasswordExecution(){

    }

    // 登陆失败的构造器
    public PasswordExecution(PasswordStateEnum stateEnum) {
        this.state = stateEnum.getState();              //状态码
        this.stateInfo = stateEnum.getStateInfo();      //状态信息
    }


    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public String toString(){
        return "PasswordExecution [state=" + state + ", stateInfo=" + stateInfo+"]";
    }

}
