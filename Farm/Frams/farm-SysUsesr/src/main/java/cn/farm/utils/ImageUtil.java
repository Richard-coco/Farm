package cn.farm.utils;

import cn.farm.entity.Image;
import cn.farm.exception.NotImageException;
import cn.farm.utils.Determiner.Determiner;
import cn.farm.utils.Determiner.ImageDeterminer;
import cn.farm.utils.Translation.Translation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Slf4j
@Component
@ConfigurationProperties(prefix = "image")
@Configuration
public class ImageUtil<E> {

    private String basePath ;

    //处理图片List map中需要放kye为target的需要转换的对象
    public  List<Image> dealImage(List<MultipartFile> multipartFileList , E e , Translation translation) throws IOException, NotImageException {
        //处理multipartFileList
        multipartFileList = FileUtil.getNotNullList(multipartFileList);
        if (multipartFileList == null) {
            //上传的文件为空
            return null;
        } else {
            if (FileUtil.doFilter(multipartFileList, new ImageDeterminer()) == false) {
                throw new NotImageException("400","插入的不是图片");
            }
        }
        int num = 1;
        Image image;
        String path;
        MultipartFile multipartFile;
        List<Image> images = new ArrayList<>();
        Iterator<MultipartFile> iterator = multipartFileList.iterator();
        while (iterator.hasNext()){
            multipartFile = iterator.next();
            image = new Image();
            path = "/SysuImg//"+UUID.randomUUID().toString().replace("-","") +"_"+SystemUtil.getTime()+"_"+num+".png";
            translation.set(e).translate(image,path);
            images.add(image);
            FileUtil.download(multipartFile,basePath+path);
            num++;
        }
        return images;
    }


    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

}
