package cn.farm.dto;

import cn.farm.pojo.Plant;

import java.util.List;

public class Category {
    private List<Plant> list;
    private Integer num;

    public Category() {
    }

    public Category(List<Plant> list, Integer num) {
        this.list = list;
        this.num = num;
    }

    public List<Plant> getList() {
        return list;
    }

    public void setList(List<Plant> list) {
        this.list = list;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
