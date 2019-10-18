package cn.farm.login.pojo;

import java.util.Date;

public class Message {
    private Integer messid;

    private String title;

    private String keyword;

    private String author;

    private Date date;

    private String webpage;

    public Integer getMessid() {
        return messid;
    }

    public void setMessid(Integer messid) {
        this.messid = messid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword == null ? null : keyword.trim();
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author == null ? null : author.trim();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getWebpage() {
        return webpage;
    }

    public void setWebpage(String webpage) {
        this.webpage = webpage == null ? null : webpage.trim();
    }
}