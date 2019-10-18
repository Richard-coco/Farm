package cn.farm.login.dto;

import cn.farm.login.pojo.Biologycategory;
import cn.farm.login.pojo.Image;
import cn.farm.login.pojo.Plant;

import java.util.List;

public class PlantInfo {
    private Plant plant;//植物信息
    private List<Image> list;//植物图片
    private Biologycategory biologycategory;//植物分类

    public PlantInfo() {
    }

    public Plant getPlant() {
        return plant;
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
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

    public PlantInfo(Plant plant, List<Image> list, Biologycategory biologycategory) {
        this.plant = plant;
        this.list = list;
        this.biologycategory = biologycategory;
    }
}
