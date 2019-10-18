package cn.farm.Mapper;

import cn.farm.Entity.Image;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;


public interface ImageMapper {

    @Options(useGeneratedKeys = true,keyProperty = "imageID")
    @Insert("insert into image(name,format,description,author,time,location,device,imageFile) " +
            "values(#{name},#{format},#{description},#{author},#{time},#{location},#{device},#{imageFile})")
    void insert(Image image);

    @Select("select * from image where imageID = #{imageID}")
    Image selectByImageID(int imageID);

    @Select("select imageFile from image where imageID = #{imageID}")
    String selectImageFileByImageID(int imageID);

    @Delete("delete from image where imageID = #{imageID}")
    void deleteByImageID(int ImageID);
}
