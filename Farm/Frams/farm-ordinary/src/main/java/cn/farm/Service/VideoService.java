package cn.farm.Service;


import cn.farm.Entity.Video;
import cn.farm.Mapper.VideoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VideoService {

    @Autowired
    VideoMapper videoMapper;

    public int insertVideo(Video video){
        videoMapper.insertVideo(video);
        return video.getVideoID();
    }
}
