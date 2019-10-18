package cn.farm.pojo;

import java.util.Date;

public class Inquiry {
    private Integer inquiryid;

    private Integer userid;

    private Integer cropid;

    private String symptom;

    private Integer image;

    private Integer video;

    private Integer spectral;

    private Date time;

    public Integer getInquiryid() {
        return inquiryid;
    }

    public void setInquiryid(Integer inquiryid) {
        this.inquiryid = inquiryid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getCropid() {
        return cropid;
    }

    public void setCropid(Integer cropid) {
        this.cropid = cropid;
    }

    public String getSymptom() {
        return symptom;
    }

    public void setSymptom(String symptom) {
        this.symptom = symptom == null ? null : symptom.trim();
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

    public Integer getVideo() {
        return video;
    }

    public void setVideo(Integer video) {
        this.video = video;
    }

    public Integer getSpectral() {
        return spectral;
    }

    public void setSpectral(Integer spectral) {
        this.spectral = spectral;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}