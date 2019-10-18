package cn.farm.dto;

import cn.farm.pojo.Enemy;

import java.util.List;

public class EnemyCategory {
    private List<Enemy> list;
    private Integer num;

    public EnemyCategory() {
    }

    public EnemyCategory(List<Enemy> list, Integer num) {
        this.list = list;
        this.num = num;
    }

    public List<Enemy> getList() {
        return list;
    }

    public void setList(List<Enemy> list) {
        this.list = list;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
