package cn.farm.utils;

import cn.farm.entity.Video;
import cn.farm.utils.Translation.Translation;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@ConfigurationProperties("video")
@Component
public class VideoUtil<E> {

    private String basePath ;

    //如果上传视频为空 返回NULL 否则返回处理好的video对象
    public Video dealVideo(MultipartFile multipartFile , E e, Translation translation) throws IOException {

        if(multipartFile == null){
            return null;
        }
        Video video = new Video();
        String path;
        path = "/SysuImg//"+UUID.randomUUID().toString().replace("-","") +"_"+SystemUtil.getTime()+"_"+".avi";
        translation.set(e);
        translation.translate(video,path);
        FileUtil.download(multipartFile,""+basePath+path);
        return video;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }
}
