package cn.farm.Service;


import cn.farm.Entity.Image;
import cn.farm.Entity.Information;
import cn.farm.Entity.MatchImage;
import cn.farm.Entity.Video;
import cn.farm.Mapper.InformationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Transactional
@Service
public class InformationService {

    @Autowired
    ImageService imageService;

    @Autowired
    MatchImageService matchImageService;

    @Autowired
    VideoService videoService;

    @Autowired
    InformationMapper informationMapper;

    public void insertInformation(List<Image> images, Information information, Video video, int userID) {
        //处理图片 如果不上传图片 则images 为null
        int matchID = 0;
        if(images != null){
            List<Integer> imageIDS = imageService.insertImageList(images);
            List<MatchImage> matchImages = new ArrayList<>();
            MatchImage matchImage;
            int imageID;
            Iterator<Integer> iterator = imageIDS.iterator();
            while(iterator.hasNext()){
                imageID = iterator.next();
                matchImage = new MatchImage();
                matchImage.setMatchinfo("胁迫信息表的胁迫图片");
                matchImage.setImageID(imageID);
                matchImages.add(matchImage);
            }
            matchID = matchImageService.insertList(matchImages);
        }
        //处理视频
        int videoID = 0 ;
        videoID = videoService.insertVideo(video);




        //处理胁迫情报 information
        information.setTime(new Date(System.currentTimeMillis()));
        information.setAuthor(userID);
        //matchID = 0  videoID = 0 说明没有上传的image和video
        if(matchID != 0){
            information.setImages(matchID);
        }
        if(videoID != 0){
            information.setVideos(videoID);
        }

        informationMapper.insert(information);
    }
}
