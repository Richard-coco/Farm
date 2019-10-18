package cn.farm.expert.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Data{

    private int code;

    private String msg;

    private JsonData1 jsondata;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return jsondata;
    }

    public void setData(JsonData1 data) {
        this.jsondata = data;

    }

}
