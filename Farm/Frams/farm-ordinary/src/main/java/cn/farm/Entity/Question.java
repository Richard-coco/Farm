package cn.farm.Entity;

import java.io.Serializable;
import java.sql.Date;

//implements Serializable
public class Question implements Serializable {

    int questID;
    int userID;
    String title;
    String content;
    int image;
    String domain;
    Date time;

    public Question(String domain, int userID, String title, String content) {
        this.domain = domain;
        this.userID = userID;
        this.title = title;
        this.content = content;
    }

    public Question(int userID, String title, String content, int image, String domain, Date time) {
        this.userID = userID;
        this.title = title;
        this.content = content;
        this.image = image;
        this.domain = domain;
        this.time = time;
    }

    public Question(int questID, int userID, String title, String content, int image, String domain, Date time) {
        this.questID = questID;
        this.userID = userID;
        this.title = title;
        this.content = content;
        this.image = image;
        this.domain = domain;
        this.time = time;
    }

    public Question() {
    }

    public int getQuestID() {
        return questID;
    }

    public void setQuestID(int questID) {
        this.questID = questID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

}


