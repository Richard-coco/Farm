package cn.farm.Message;

import java.util.List;

public class ListMessage extends Common {

    List list;

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public ListMessage(List list) {
        this.list = list;
    }

    public ListMessage(Integer code, String message, List list) {
        super(code, message);
        this.list = list;
    }
}
