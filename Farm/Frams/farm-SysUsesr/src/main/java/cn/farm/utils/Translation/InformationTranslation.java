package cn.farm.utils.Translation;

import cn.farm.entity.Image;
import cn.farm.entity.Information;

import java.sql.Date;

public class InformationTranslation implements Translation<Information,Image> {
    Information information;

    @Override
    public void translate(Image image, String path) {
        image.setName("情报胁迫图像");
        image.setFormat("png");
        image.setTime(new Date(System.currentTimeMillis()));
        image.setAuthor("zuojunZzz");
        image.setImagefile(path);
    }

    @Override
    public Translation set(Information information) {
        this.information = information;
        return this;
    }
}
