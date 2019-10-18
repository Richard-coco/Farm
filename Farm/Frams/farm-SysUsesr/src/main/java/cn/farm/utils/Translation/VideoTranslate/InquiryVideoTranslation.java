package cn.farm.utils.Translation.VideoTranslate;

import cn.farm.entity.Inquiry;
import cn.farm.entity.Video;
import cn.farm.utils.Translation.Translation;

import java.util.Date;

public class InquiryVideoTranslation implements Translation<Inquiry,Video> {

     Inquiry inquiry;
    @Override
    public void translate(Video video, String path) {
        video.setName("胁迫视频");
        video.setTime(new Date(System.currentTimeMillis()));
        video.setFormat(".avi");
        video.setDescription(inquiry.getSymptom());
        video.setVideofile(path);
    }

    @Override
    public Translation set(Inquiry inquiry) {
        this.inquiry = inquiry;
        return this;
    }
}
