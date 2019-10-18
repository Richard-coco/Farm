package cn.farm.mapper;

import cn.farm.entity.Image;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageMapperSql {
    @Insert("insert into image(name,format,description,author,time,location,device,imageFile) values(#{name},#{format},#{description},#{author},#{time},#{location},#{device},#{imagefile})")
    @Options(useGeneratedKeys = true,keyProperty = "imageid",keyColumn = "imageID")
    int insert(Image image);

    @Delete("delete from image where imageID = #{imageid}")
    void delete(Integer imageid);

    @Update("update image set imageFile = #{imagefile} where imageID = #{imageid}")
    void update(Image image);

    @Select("selelct * from image where imageID = #{imageid}")
    void select(Integer imageid);

}
