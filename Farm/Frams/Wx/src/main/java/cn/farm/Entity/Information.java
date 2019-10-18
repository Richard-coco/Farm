package cn.farm.Entity;

public class Information {

    Integer infoID;
    String link;
    String imageSrc;
    String title;
    String playCount;
    String commentCount;
    String videoSrc;

    public Integer getInfoID() {
        return infoID;
    }

    public void setInfoID(Integer infoID) {
        this.infoID = infoID;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlayCount() {
        return playCount;
    }

    public void setPlayCount(String playCount) {
        this.playCount = playCount;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

    public String getVideoSrc() {
        return videoSrc;
    }

    public void setVideoSrc(String videoSrc) {
        this.videoSrc = videoSrc;
    }

    public Information() {
    }

    public Information(Integer infoID, String link, String imageSrc, String title, String playCount, String commentCount, String videoSrc) {
        this.infoID = infoID;
        this.link = link;
        this.imageSrc = imageSrc;
        this.title = title;
        this.playCount = playCount;
        this.commentCount = commentCount;
        this.videoSrc = videoSrc;
    }
}
