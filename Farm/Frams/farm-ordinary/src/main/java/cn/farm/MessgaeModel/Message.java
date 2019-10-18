package cn.farm.MessgaeModel;

public class Message<E> extends Common {

    E e ;

    public Message(int code, String message, E e) {
        super(code, message);
        this.e = e;
    }

    public Message(E e) {
        this.e = e;
    }

    public E getE() {
        return e;
    }

    public void setE(E e) {
        this.e = e;
    }
}
