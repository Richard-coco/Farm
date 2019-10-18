package cn.farm.Mapper;


import cn.farm.Entity.Information;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface InformationMapper {

    @Insert("insert into information(title,content,author,time,location,images,videos)" +
            " values(#{title},#{content},#{author},#{time},#{location},#{images},#{videos})")
    int insert(Information information);

    @Select("select * from information where author = #{id}")
    List<Information> select(int id);

    @Delete("delete from information where infoID = #{infoID}")
    int delete(int infoID);

    @Update("update information set title=#{title},content=#{content},author=#{author},time=#{time},location=#{location} where infoID = #{infoID}")
    int update(Information information);
}

