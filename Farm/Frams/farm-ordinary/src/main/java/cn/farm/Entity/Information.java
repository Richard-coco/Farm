package cn.farm.Entity;

import java.sql.Date;

public class Information {

    Integer infoID;
    String title;
    String content;
    Integer author;
    Date time;
    String location;
    Integer images;
    Integer videos;

    public Information() {
    }

    public Information(String title, String content, Integer author, String location) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.location = location;
    }

    public Information(Integer infoID, String title, String content, Integer author, Date time, String location, Integer images, Integer videos) {
        this.infoID = infoID;
        this.title = title;
        this.content = content;
        this.author = author;
        this.time = time;
        this.location = location;
        this.images = images;
        this.videos = videos;
    }

    public Integer getInfoID() {
        return infoID;
    }

    public void setInfoID(Integer infoID) {
        this.infoID = infoID;
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

    public Integer getAuthor() {
        return author;
    }

    public void setAuthor(Integer author) {
        this.author = author;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getImages() {
        return images;
    }

    public void setImages(Integer images) {
        this.images = images;
    }

    public Integer getVideos() {
        return videos;
    }

    public void setVideos(Integer videos) {
        this.videos = videos;
    }
}
