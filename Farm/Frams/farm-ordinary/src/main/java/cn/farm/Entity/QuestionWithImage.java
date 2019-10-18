package cn.farm.Entity;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

public class QuestionWithImage extends Question {

    Map<String,String> map;

    public QuestionWithImage(int userID, String title, String content, int image, String domain, Date time, HashMap<String, String> map) {
        super(userID, title, content, image, domain, time);
        this.map = map;
    }

    public QuestionWithImage() {
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public QuestionWithImage(Question question ,HashMap<String, String> map ){
        super(question.getUserID(),question.getTitle(),question.getContent(),question.getImage(),question.getDomain(),question.getTime());
        this.map = map;
    }

//    List list;
//
//    public QuestionWithImage(int userID, String title, String content, int image, String domain, Date time, List list) {
//        super(userID, title, content, image, domain, time);
//        this.list = list;
//    }
//
//    public QuestionWithImage() {
//    }
//
//    public QuestionWithImage(int questID, int userID, String title, String content, int image, String domain, Date time, List list) {
//        super(questID, userID, title, content, image, domain, time);
//        this.list = list;
//    }
//
//    public QuestionWithImage(List list) {
//        this.list = list;
//    }
//
//    public List getList() {
//        return list;
//    }
//
//    public void setList(List list) {
//        this.list = list;
//    }
}
