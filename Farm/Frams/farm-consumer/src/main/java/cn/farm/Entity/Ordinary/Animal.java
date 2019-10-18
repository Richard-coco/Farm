package cn.farm.Entity.Ordinary;

import java.util.List;

public class Animal {
    int id;
    List list;

    public Animal() {
    }

    public Animal(int id, List list) {
        this.id = id;
        this.list = list;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }
}
