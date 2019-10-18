package cn.farm.Util.Translation;



import cn.farm.Entity.Information;
import cn.farm.Entity.Video;

import java.sql.Date;

public class VideoInformationTranslation implements Translation<Information, Video> {

    Information information;

    @Override
    public void translate(Video video, String path) {
        video.setName(information.getTitle());
        video.setDescription(information.getContent());
        video.setFormat("avi");
        video.setTime(new Date(System.currentTimeMillis()));
        video.setLocation(information.getLocation());
        video.setVideoFile(path);
    }

    @Override
    public Translation set(Information information) {
        this.information = information;
        return this;
    }
}
