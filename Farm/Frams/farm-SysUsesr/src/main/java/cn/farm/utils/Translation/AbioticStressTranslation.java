package cn.farm.utils.Translation;

import cn.farm.entity.Abioticstress;
import cn.farm.entity.Image;

import java.sql.Date;

public class AbioticStressTranslation implements Translation<Abioticstress,Image> {
    Abioticstress abioticstress;
    @Override
    public void translate(Image image, String path) {
        image.setName("非生物");
        image.setFormat("png");
        image.setTime(new Date(System.currentTimeMillis()));
        image.setAuthor("zuojunZzz");
        image.setImagefile(path);
    }

    @Override
    public Translation set(Abioticstress abioticstress) {
        this.abioticstress = abioticstress;
        return this;
    }
}
