package cn.farm.Entity;

import java.sql.Date;

public class Video {
    int videoID;
    String name;
    String format;
    String description;
    String author;
    Date time;
    String location;
    String device;
    String videoFile;

    public int getVideoID() {
        return videoID;
    }

    public void setVideoID(int videoID) {
        this.videoID = videoID;
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

    public String getVideoFile() {
        return videoFile;
    }

    public void setVideoFile(String videoFile) {
        this.videoFile = videoFile;
    }

    public Video(int videoID, String name, String format, String description, String author, Date time, String location, String device, String videoFile) {
        this.videoID = videoID;
        this.name = name;
        this.format = format;
        this.description = description;
        this.author = author;
        this.time = time;
        this.location = location;
        this.device = device;
        this.videoFile = videoFile;
    }

    public Video() {
    }

    @Override
    public String toString() {
        return "Video{" +
                "videoID=" + videoID +
                ", name='" + name + '\'' +
                ", format='" + format + '\'' +
                ", description='" + description + '\'' +
                ", author='" + author + '\'' +
                ", time=" + time +
                ", location='" + location + '\'' +
                ", device='" + device + '\'' +
                ", videoFile='" + videoFile + '\'' +
                '}';
    }
}
