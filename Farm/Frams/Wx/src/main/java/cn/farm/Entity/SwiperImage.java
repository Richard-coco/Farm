package cn.farm.Entity;

public class SwiperImage {

    String link;
    String imageSrc;

    public SwiperImage() {
    }

    public SwiperImage(String link, String imageSrc) {
        this.link = link;
        this.imageSrc = imageSrc;
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
}
