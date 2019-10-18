package cn.farm.login.mapper;

import cn.farm.login.pojo.Image;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ImageMapper extends Mapper<Image> {
    @Select("SELECT * FROM image WHERE imageID = #{imageid}")
    List<Image> selectByKey(@Param(value = "imageid") Integer imageid);
}
