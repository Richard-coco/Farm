package cn.farm.mapper;

import cn.farm.entity.Protection;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProtectionMapper {
    @Select("select * from protection where title = #{title} and keyword = #{keyword} and author = #{author} and webpage = #{webpage}")
     Integer select(Protection protection);

    @Insert("insert into protection(title,keyword,author,date,webpage) values(#{title},#{keyword},#{author},#{date},#{webpage})")
    void insert(Protection protection);

    @Delete("delete from protection where knowID = #{knowid}")
    void delete(Integer knowid);

    @Select("select * from protection where knowID = #{knowid}")
    Integer selectById(Integer knowid);

    @Select("select * from protection")
    List<Protection> selectAll();

    @Update("update protection set title = #{title},keyword = #{keyword},author = #{author},webpage = #{webpage} where knowid = #{knowid}")
    Integer modify(Protection protection);
}
