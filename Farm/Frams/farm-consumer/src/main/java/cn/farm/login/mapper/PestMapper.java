package cn.farm.login.mapper;

import cn.farm.login.pojo.Peststress;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PestMapper {
    @Select("select * from peststress where chineseName like CONCAT('%',#{name},'%')")
    List<Peststress> likeSelect(@Param(value = "name") String name);


    @Select("select count(*) from peststress where category in (select bioID from biologycategory where kingdom = #{kingdom}and phylum = #{phylum} and class = #{class}and bio_order = #{bio_order} ) ")
    Integer selectByCate1(@Param(value = "kingdom") String kingdom, @Param(value = "phylum") String phylum,
                          @Param(value = "class") String _class, @Param(value = "bio_order") String bio_order);

    @Select("select count(*) from peststress where category in (select bioID from biologycategory where kingdom = #{kingdom}and phylum = #{phylum} and class = #{class} ) ")
    Integer selectByCate2(@Param(value = "kingdom") String kingdom, @Param(value = "phylum") String phylum,
                          @Param(value = "class") String _class);
    @Select("select count(*) from peststress where category in (select bioID from biologycategory where kingdom = #{kingdom}and phylum = #{phylum} ) ")
    Integer selectByCate3(@Param(value = "kingdom") String kingdom, @Param(value = "phylum") String phylum);
    @Select("select count(*) from peststress where category in (select bioID from biologycategory where kingdom = #{kingdom}) ")
    Integer selectByCate4(@Param(value = "kingdom") String kingdom);


    @Select("select * from peststress where category in (select bioID from biologycategory where kingdom = #{kingdom}and phylum = #{phylum} and class = #{class}and bio_order = #{bio_order} ) ")
    List<Peststress> selectByCategory1(@Param(value = "kingdom") String kingdom, @Param(value = "phylum") String phylum,
                                       @Param(value = "class") String _class, @Param(value = "bio_order") String bio_order);

    @Select("select * from peststress where category in (select bioID from biologycategory where kingdom = #{kingdom}and phylum = #{phylum} and class = #{class} ) ")
    List<Peststress> selectByCategory2(@Param(value = "kingdom") String kingdom, @Param(value = "phylum") String phylum,
                                       @Param(value = "class") String _class);
    @Select("select * from peststress where category in (select bioID from biologycategory where kingdom = #{kingdom}and phylum = #{phylum} and  ) ")
    List<Peststress> selectByCategory3(@Param(value = "kingdom") String kingdom, @Param(value = "phylum") String phylum);
    @Select("select * from peststress where category in (select bioID from biologycategory where kingdom = #{kingdom} ) ")
    List<Peststress> selectByCategory4(@Param(value = "kingdom") String kingdom);
}
