package cn.farm.utils.Translation.VideoTranslate;

import cn.farm.entity.Information;
import cn.farm.entity.Video;
import cn.farm.utils.Translation.Translation;

import java.util.Date;

public class InformationVideoTranslation implements Translation<Information, Video> {

    private Information information;


    @Override
    public void translate(Video video, String path) {
        video.setName(information.getTitle());
        video.setTime(new Date(System.currentTimeMillis()));
        video.setFormat(".avi");
        video.setLocation(information.getLocation());
        video.setDescription(information.getContent());
        video.setVideofile(path);
    }

    @Override
    public Translation set(Information information) {
        this.information = information;
        return this;
    }
}
