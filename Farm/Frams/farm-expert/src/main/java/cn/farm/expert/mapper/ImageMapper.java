package cn.farm.expert.mapper;

import cn.farm.expert.domain.Image;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

public interface ImageMapper {

    @Insert("insert into `image` values (#{imageId},#{name},#{format},#{description},#{author},#{time},#{location},#{device},#{imageFile});")
    int save(Image image);

    @Select("select imageId from image where imageFile = #{imageFile}")
    int selectImageId(String path);
}
