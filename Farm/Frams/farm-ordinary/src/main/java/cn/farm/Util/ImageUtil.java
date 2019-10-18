package cn.farm.Util;

import cn.farm.Conf.Exception.NotImageException;
import cn.farm.Entity.Image;
import cn.farm.Util.Determiner.ImageDeterminer;
import cn.farm.Util.Translation.Translation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Component
@ConfigurationProperties(prefix = "image")
public class ImageUtil<E> {


    private String basePath ;

    public ImageUtil() {
        log.info("ImageUtil:加载完成");
    }

    //处理图片List map中需要放kye为target的需要转换的对象
    public  List<Image> dealImage(List<MultipartFile> multipartFileList , E e , Translation translation) throws IOException, NotImageException {
        //处理multipartFileList
        multipartFileList = FileUtil.getNotNullList(multipartFileList);
        if (multipartFileList == null) {
            //上传的文件为空
            return null;
        } else {
            if (FileUtil.doFilter(multipartFileList, new ImageDeterminer()) == false) {
                throw new NotImageException();
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
            path ="hashcode_"+e.toString()+"_"+SystemUtil.getTime()+"_"+num+".png";
            translation.set(e).translate(image,basePath+path);
            images.add(image);
            FileUtil.download(multipartFile,basePath+path);
            num++;
        }
        return images;
    }

    public String downloadOneImage(MultipartFile multipartFile, E e) throws IOException {
        String path = "hashcode_"+e.toString()+"_"+SystemUtil.getTime()+".png";
        FileUtil.download(multipartFile,basePath+path);
        return path;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

}
