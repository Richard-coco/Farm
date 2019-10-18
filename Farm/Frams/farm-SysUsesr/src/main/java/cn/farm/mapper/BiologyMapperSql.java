package cn.farm.mapper;

import cn.farm.entity.Biologycategory;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BiologyMapperSql {
    @Insert("insert into biologycategory(kingdom,phylum,class,bio_order,family,genus,species) values(#{kingdom},#{phylum},#{bio_class},#{order},#{family},#{genus},#{species})")
    @Options(useGeneratedKeys = true,keyProperty = "bioid",keyColumn = "bioID")
    int insert(Biologycategory biologycategory);

    @Select("select bioid from biologycategory where species = #{species}")
    int select(String species);


    @Select("select * from biologycategory where kingdom = #{kingdom} and phylum = #{phylum} and class = #{bio_class} and bio_order = #{order} and family = #{family} and genus = #{genus} and species = #{species} ")
    Integer selectBio(Biologycategory biologycategory);

    @Select("select bioid from biologycategory where kingdom = #{kingdom} and phylum = #{phylum} and class = #{bio_class} and bio_order = #{order} and family = #{family} and genus = #{genus} and species = #{species}")
    Biologycategory selectId(Biologycategory biologycategory);


    @Select("select bioID from biologycategory where bioID = #{bioid}")
    int selectById(Integer bioid);
}
