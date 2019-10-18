package cn.farm.utils.Translation;

import cn.farm.entity.Image;
import cn.farm.entity.Question;

import java.sql.Date;

public class QuestionTranslation implements Translation<Question,Image>{

    Question question;

    @Override
    public void translate(Image image, String path) {
        image.setTime(new Date(System.currentTimeMillis()));
        image.setName(question.getTitle());
        image.setFormat("png");
        image.setAuthor("zuojunZzz");
        image.setDescription(question.getContent());
        image.setImagefile(path);
    }

    @Override
    public Translation set(Question question) {
        this.question = question;
        return this;
    }
}
