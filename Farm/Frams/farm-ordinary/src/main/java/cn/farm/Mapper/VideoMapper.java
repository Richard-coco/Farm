package cn.farm.Mapper;


import cn.farm.Entity.Video;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;

public interface VideoMapper {

    @Insert("insert into video(name,format,description,time,location,videoFile) " +
                    "  values(#{name},#{format},#{description},#{time},#{location},#{videoFile})")
    @Options(useGeneratedKeys = true,keyProperty = "videoID")
    void insertVideo(Video video);
}
