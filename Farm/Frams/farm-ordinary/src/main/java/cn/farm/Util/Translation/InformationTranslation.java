package cn.farm.Util.Translation;


import cn.farm.Entity.Image;
import cn.farm.Entity.Information;

import java.sql.Date;

public class InformationTranslation implements Translation<Information, Image> {

    Information information;

    @Override
    public void translate(Image image, String path) {
        image.setTime(new Date(System.currentTimeMillis()));
        image.setName(information.getTitle());
        image.setDescription(information.getContent());
        image.setImageFile(path);
        image.setLocation(information.getLocation());
        image.setFormat("png");
    }

    @Override
    public Translation set(Information information) {
        this.information = information;
        return this;
    }
}
