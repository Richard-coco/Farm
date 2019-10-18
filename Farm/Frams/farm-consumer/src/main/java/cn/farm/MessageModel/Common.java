package cn.farm.MessageModel;

import java.io.Serializable;

public class Common implements Serializable {
    int code;
    String message;

    public Common() {
    }

    public Common(int code, String message){
        this.code = code;
        this.message = message;

    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
