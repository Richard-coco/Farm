package cn.farm.mapper;

import cn.farm.entity.Stresscategory;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StressCategoryMapper {
    @Select("select * from stresscategory where name = #{name} and mainType = #{maintype} and subType = #{subtype} and factor = #{factor}")
    Integer select(Stresscategory stresscategory);

    @Insert("insert into stresscategory(name,mainType,subType,factor) values(#{name},#{maintype},#{subtype},#{factor})")
    @Options(useGeneratedKeys = true,keyProperty = "stressid",keyColumn = "stressID")
    void add(Stresscategory stresscategory);

    @Select("select stressID from stresscategory where  name = #{name} and mainType = #{maintype} and subType = #{subtype} and factor = #{factor}")
    Stresscategory selectId(Stresscategory stresscategory);

    @Select("select stressID from stresscategory where  name = #{stressname}")
    Integer selectByName(String stressname);
    @Select("select stressID from stresscategory where stressID = #{stressid}")
    Integer selectById(Integer stressid);
}
