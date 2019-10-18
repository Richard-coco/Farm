package cn.farm.utils.Translation;

import cn.farm.entity.Enemy;
import cn.farm.entity.Image;

import java.sql.Date;

public class EnenmyTranslation implements Translation<Enemy,Image> {
   Enemy enemy;
    @Override
    public void translate(Image image, String path) {
        image.setName(enemy.getChinesename());
        image.setDescription(enemy.getDistribution());
        image.setFormat("png");
        image.setTime(new Date(System.currentTimeMillis()));
        image.setAuthor("zuojunZzz");
        image.setImagefile(path);
    }

    @Override
    public Translation set(Enemy enemy) {
        this.enemy =enemy;
        return this;
    }
}
