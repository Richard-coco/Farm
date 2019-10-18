package cn.farm.Util;


import cn.farm.Entity.Video;
import cn.farm.Util.Translation.Translation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Component
@ConfigurationProperties(prefix = "video")
public class VideoUtil<E> {

    private String basePath;

    public VideoUtil() {
        log.info("VideoUtil:加载完成");
    }

    public Video dealVideo(MultipartFile multipartFile, E e , Translation<E,Video> translation) throws IOException {
        if(multipartFile == null){
            return null;
        }
        Video video;
        String path = "hashcode_"+multipartFile.toString()+"_"+SystemUtil.getTime()+".avi";
        FileUtil.download(multipartFile,basePath+path);
        video = new Video();
        translation.set(e).translate(video,basePath+path);
        return video;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }
}
