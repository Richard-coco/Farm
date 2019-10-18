package cn.farm.mapper;

import cn.farm.entity.Video;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoMapper {


    @Insert("insert into video(name,format,description,author,time,location,device,videoFile) values(#{name},#{format},#{description},#{author},#{time},#{location},#{device},#{videofile})")
    @Options(useGeneratedKeys = true,keyProperty = "videoID",keyColumn = "videoid")
    void add(Video video);


    @Select("select videoID from video where videoID = #{videoid}")
    Integer getId(Integer videoid);

    @Select("select videoID from video where name = #{name} and description = #{description}")
    int selectByName(String name,String description);
}
