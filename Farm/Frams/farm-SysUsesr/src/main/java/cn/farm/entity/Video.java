package cn.farm.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Video {
    private Integer videoid;

    private String name;

    private String format;

    private String description;

    private String author;

    private Date time;

    private String location;

    private String device;

    private String videofile;

}