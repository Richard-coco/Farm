package cn.farm.utils.Translation;

import cn.farm.entity.Image;
import cn.farm.entity.Weedstress;

import java.sql.Date;

public class WeedStressTranslation implements Translation<Weedstress,Image>{
   Weedstress weedstress;
    @Override
    public void translate(Image image, String path) {
        image.setName("草害");
        image.setFormat("png");
        image.setTime(new Date(System.currentTimeMillis()));
        image.setAuthor("zuojunZzz");
        image.setImagefile(path);
    }

    @Override
    public Translation set(Weedstress weedstress) {
        this.weedstress =weedstress;
        return this;
    }
}
