package cn.farm.utils.Translation;

import cn.farm.entity.Image;
import cn.farm.entity.Plant;
import java.sql.Date;

public class PlantTranslation implements Translation<Plant,Image> {

    Plant plant;

    @Override
    public void translate(Image image, String path) {
        image.setName(plant.getChinesename());
        image.setDescription(plant.getDistribution());
        image.setFormat("png");
        image.setTime(new Date(System.currentTimeMillis()));
        image.setImagefile(path);
    }

    @Override
    public Translation set(Plant plant) {
        this.plant = plant;
        return this;
    }
}
