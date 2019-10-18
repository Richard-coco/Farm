package cn.farm.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Information {
    private Integer infoid;

    private String title;

    private String content;

    private Integer author;

    private Date time;

    private String location;

    private Integer images;

    private Integer videos;

    public Information(String title, String content, Integer author, Date time, String location, Integer images, Integer videos) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.time = time;
        this.location = location;
        this.images = images;
        this.videos = videos;
    }
}