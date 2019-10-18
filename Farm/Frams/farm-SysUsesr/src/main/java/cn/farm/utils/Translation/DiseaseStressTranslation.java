package cn.farm.utils.Translation;

import cn.farm.entity.Diseasestress;
import cn.farm.entity.Image;

import java.sql.Date;

public class DiseaseStressTranslation implements Translation<Diseasestress,Image> {
    Diseasestress diseasestress;
    @Override
    public void translate(Image image, String path) {
        image.setName(diseasestress.getChinesename());
        image.setDescription(diseasestress.getDistribution());
        image.setFormat("png");
        image.setTime(new Date(System.currentTimeMillis()));
        image.setAuthor("zuojunZzz");
        image.setImagefile(path);
    }

    @Override
    public Translation set(Diseasestress diseasestress) {
        this.diseasestress = diseasestress;
        return this;
    }
}
