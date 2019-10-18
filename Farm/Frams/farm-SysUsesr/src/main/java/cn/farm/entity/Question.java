package cn.farm.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Question {
    private Integer questid;
    private Integer userid;

    private String title;

    private String content;

    private Integer image;

    private String domain;

    private Date time;

    public Question(Integer userid, String title, String content, Integer image, String domain, Date time) {
        this.userid = userid;
        this.title = title;
        this.content = content;
        this.image = image;
        this.domain = domain;
        this.time = time;
    }


    public Integer getUserID() {
      return   userid;
    }
}