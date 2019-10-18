package cn.farm.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Message {
    private Integer messid;

    private String title;

    private String keyword;

    private String author;

    private Date date;

    private String webpage;

    public Message(String title, String keyword, String author, Date date, String webpage) {
        this.title = title;
        this.keyword = keyword;
        this.author = author;
        this.date = date;
        this.webpage = webpage;
    }
}