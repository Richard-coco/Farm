package cn.farm.login.dto;

import cn.farm.login.enums.SessionStateEnum;

public class SessionExecution {
    private Integer state;          //状态码
    private String stateInfo;   //该状态下信息

    public SessionExecution(){

    }

    // 登陆失败的构造器
    public SessionExecution(SessionStateEnum stateEnum) {
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
        return "SessionExecution [state=" + state + ", stateInfo=" + stateInfo+"]";
    }

}
