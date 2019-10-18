package cn.farm.Util.Translation;



import cn.farm.Entity.Image;
import cn.farm.Entity.Question;

import java.sql.Date;

public class QuestionTranslation implements Translation<Question, Image>{

    Question question;

    @Override
    public void translate(Image image,String path) {
        image.setTime(new Date(System.currentTimeMillis()));
        image.setName(question.getTitle());
        image.setFormat("png");
        image.setDescription(question.getContent());
        image.setImageFile(path);
    }

    @Override
    public Translation set(Question question) {
        this.question = question;
        return this;
    }
}
