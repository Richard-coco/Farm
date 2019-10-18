package cn.farm.utils.Translation;
import cn.farm.entity.Image;
import cn.farm.entity.User;

import java.sql.Date;

public class UserTranslation implements Translation<User, Image> {
   User user;
    @Override
    public void translate(Image image, String path) {
        image.setName(user.getNickname());
        image.setDescription(user.getName()+"用户插入图片");
        image.setFormat("png");
        image.setTime(new Date(System.currentTimeMillis()));
        image.setAuthor("zuojunZzz");
        image.setImagefile(path);
    }

    @Override
    public Translation set(User user) {
        this.user = user;
        return this;
    }
}
