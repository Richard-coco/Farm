package cn.farm.dto;

import cn.farm.pojo.Biologycategory;
import cn.farm.pojo.Enemy;
import cn.farm.pojo.Image;

import java.util.List;

public class EnemyInfo {
    private Enemy enemy;//植物信息
    private List<Image> list;//植物图片
    private Biologycategory biologycategory;//植物分类

    public EnemyInfo() {
    }

    public Enemy getEnemy() {
        return enemy;
    }

    public void setEnemy(Enemy enemy) {
        this.enemy = enemy;
    }

    public List<Image> getList() {
        return list;
    }

    public void setList(List<Image> list) {
        this.list = list;
    }

    public Biologycategory getBiologycategory() {
        return biologycategory;
    }

    public void setBiologycategory(Biologycategory biologycategory) {
        this.biologycategory = biologycategory;
    }

    public EnemyInfo(Enemy enemy, List<Image> list, Biologycategory biologycategory) {
        this.enemy = enemy;
        this.list = list;
        this.biologycategory = biologycategory;
    }
}
