package cn.farm.utils.Translation;

import cn.farm.entity.Image;
import cn.farm.entity.Peststress;

import java.sql.Date;

public class PestStressTranslation implements Translation<Peststress,Image> {
    Peststress peststress;
    @Override
    public void translate(Image image, String path) {
        image.setName(peststress.getChinesename());
        image.setDescription(peststress.getDistribution());
        image.setFormat("png");
        image.setTime(new Date(System.currentTimeMillis()));
        image.setAuthor("zuojunZzz");
        image.setImagefile(path);
    }

    @Override
    public Translation set(Peststress peststress) {
        this.peststress = peststress;
        return this;
    }
}
