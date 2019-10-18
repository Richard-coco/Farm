package cn.farm.Controller;


import cn.farm.Aop.IsLogin;
import cn.farm.Conf.Exception.NotImageException;
import cn.farm.Entity.Image;
import cn.farm.Entity.Information;
import cn.farm.Entity.Video;
import cn.farm.MessgaeModel.Common;
import cn.farm.Service.ImageService;
import cn.farm.Service.InformationService;
import cn.farm.Service.MatchImageService;
import cn.farm.Service.VideoService;
import cn.farm.Util.ImageUtil;
import cn.farm.Util.Translation.InformationTranslation;
import cn.farm.Util.Translation.VideoInformationTranslation;
import cn.farm.Util.VideoUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class InformationController {


    @Autowired
    VideoUtil<Information> videoUtil;

    @Autowired
    ImageUtil<Information> imageUtil;

    @Autowired
    ImageService imageService;

    @Autowired
    MatchImageService matchImageService;

    @Autowired
    VideoService videoService;

    @Autowired
    InformationService informationService;


    @IsLogin
    @PostMapping(value = "info/insert",produces = "application/json")
    public Common insertInfomation(HttpSession session , String title, String content ,String location , int author,
                                   @RequestParam(required = false,value ="image1") MultipartFile image1,
                                   @RequestParam(required = false,value ="image2") MultipartFile image2,
                                   @RequestParam(required = false,value ="image3") MultipartFile image3,
                                   @RequestParam(required = false,value ="video") MultipartFile video1){
        Information information = new Information(title,content,author,location);
        List<MultipartFile> multipartFileList = new ArrayList<>();
        multipartFileList.add(image1);
        multipartFileList.add(image2);
        multipartFileList.add(image3);
        List<Image> images;
        try {
            images = imageUtil.dealImage(multipartFileList,information,new InformationTranslation());
        } catch (IOException e) {
            e.printStackTrace();
            return new Common(500,"server error");
        } catch (NotImageException e) {
            e.printStackTrace();
            return new Common(503,"插入的不是图片");
        }

        Video video;
        try {
            video = videoUtil.dealVideo(video1,information,new VideoInformationTranslation());
        } catch (IOException e) {
            e.printStackTrace();
            log.error("server error : deal video fail : insert information fail");
            return new Common(500,"server error");
        }

        try {
            informationService.insertInformation(images,information,video,author);
        }catch (Exception e){
            e.printStackTrace();
            log.error("SQL error : insert information fail");
            return new Common(500,"server error");
        }


        return new Common(200,"ok");
    }

}
