package cn.farm.service.impl;

import cn.farm.entity.Video;
import cn.farm.mapper.VideoMapper;
import cn.farm.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VideoServiceImpl implements VideoService {
    @Autowired
    VideoMapper videoMapper;

    @Transactional
    @Override
    public Integer addVideo(Video video) {
        videoMapper.add(video);
        int videoid = videoMapper.selectByName(video.getName(), video.getDescription());
        return videoid;
    }

}
