package cn.farm.mapper;

import cn.farm.entity.Information;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InformationMapper {
    @Insert("insert into information(title,content,author,time,location,images,videos) values(#{title},#{content},#{author},#{time},#{location},#{images},#{videos})")
    void add(Information information);

    @Select("select * from information where infoID = #{infoid}")
    Integer selectById(Integer infoid);

    @Delete("delete from information where infoID = #{infoid}")
    void delete(Integer infoid);

    @Select("select * from information")
    List<Information> selectAll();

    @Insert("update information set title = #{title},content =#{content},author = #{author},location = #{location},images = #{images},videos = #{videos} where infoID = #{infoid}")
    Integer modify(Information information);

    @Select("select infoID from information where infoID = #{infoid}")
    Integer selectId(Integer infoid);

}
