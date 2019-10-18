package cn.farm.login.mapper;

import cn.farm.login.pojo.Biologycategory;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CategoryMapper {
    @Select("select * from biologycategory where bioID = #{category}")
    Biologycategory selectByKey(@Param(value = "category") Integer category);
    @Select("select class from biologycategory where bioID = #{category}")
    String selectClass(@Param(value = "category") Integer category);

    @Select("select bio_order from biologycategory where bioID = #{category}")
    String selectOrder(@Param(value = "category") Integer category);
//=============================================================================================
    //找门
    @Select("select distinct phylum from biologycategory where kingdom = #{value}")
    List<String> foundByType1(@Param(value = "value") String value);
    //找纲
    @Select("select distinct class from biologycategory where phylum = #{value}")
    List<String> foundByType2(@Param(value = "value") String value);
    //找目
    @Select("select distinct bio_order from biologycategory where class = #{value}")
    List<String> foundByType3(@Param(value = "value") String value);
}
