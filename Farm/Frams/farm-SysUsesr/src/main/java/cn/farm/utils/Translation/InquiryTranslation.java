package cn.farm.utils.Translation;

import cn.farm.entity.Image;
import cn.farm.entity.Inquiry;

import java.sql.Date;

public class InquiryTranslation implements Translation<Inquiry, Image> {
    Inquiry inquiry;
    @Override
    public void translate(Image image, String path) {
        image.setName("胁迫图像");
        image.setFormat("png");
        image.setTime(new Date(System.currentTimeMillis()));
        image.setAuthor("zuojunZzz");
        image.setImagefile(path);
    }

    @Override
    public Translation set(Inquiry inquiry) {
        this.inquiry = inquiry;
        return this;
    }
}
