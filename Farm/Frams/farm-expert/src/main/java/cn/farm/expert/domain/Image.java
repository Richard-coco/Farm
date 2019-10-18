package cn.farm.expert.domain;

import java.util.Date;

public class Image {

    private int imageId;

    private String name;

    private String format;

    private String description;

    private String author;

    private Date time;

    private String location;

    private String device;

    private String imageFile;

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
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

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getImageFile() {
        return imageFile;
    }

    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }

    @Override
    public String toString() {
        return "Image{" +
                "imageId=" + imageId +
                ", name='" + name + '\'' +
                ", format='" + format + '\'' +
                ", description='" + description + '\'' +
                ", author='" + author + '\'' +
                ", time=" + time +
                ", location='" + location + '\'' +
                ", device='" + device + '\'' +
                ", imageFile='" + imageFile + '\'' +
                '}';
    }
}
