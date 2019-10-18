package cn.farm.MessgaeModel;

import java.util.List;

public class ListMessage<E> extends Common{

    List<E> list ;

    public ListMessage(int code, String message,List<E> list) {
        super(code, message);
        this.list = list;
    }

    public List<E> getList() {
        return list;
    }

    public void setList(List<E> list) {
        this.list = list;
    }
}
