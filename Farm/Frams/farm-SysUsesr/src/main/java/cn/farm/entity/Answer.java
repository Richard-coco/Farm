package cn.farm.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Answer {
    private Integer answerid;

    private Integer questid;

    private Integer expertid;

    private String content;

    private Date time;


}