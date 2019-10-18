package cn.farm.pojo;

import java.util.Date;

public class Answer {
    private Integer answerid;

    private Integer questid;

    private Integer expertid;

    private String content;

    private Date time;

    public Integer getAnswerid() {
        return answerid;
    }

    public void setAnswerid(Integer answerid) {
        this.answerid = answerid;
    }

    public Integer getQuestid() {
        return questid;
    }

    public void setQuestid(Integer questid) {
        this.questid = questid;
    }

    public Integer getExpertid() {
        return expertid;
    }

    public void setExpertid(Integer expertid) {
        this.expertid = expertid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}