package cn.farm.mapper;

import cn.farm.pojo.Image;
import cn.farm.pojo.Plant;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface PlantMapper extends Mapper<Plant> {
    @Select("select * from plant where chineseName like CONCAT('%',#{name},'%')")
    List<Plant> likeSelect(@Param(value = "name") String name);

    @Select("select * from plant where plantID =#{plantid}")
    Plant selectByKey(@Param(value = "plantid")Integer plantid);

    @Select("select count(*) from plant where category in (select bioID from biologycategory where kingdom = #{kingdom}and phylum = #{phylum} and class = #{class}and bio_order = #{bio_order} ) ")
    Integer selectByCate1(@Param(value = "kingdom")String kingdom,@Param(value = "phylum") String phylum,
                         @Param(value = "class")String _class, @Param(value = "bio_order")String bio_order);

    @Select("select count(*) from plant where category in (select bioID from biologycategory where kingdom = #{kingdom}and phylum = #{phylum} and class = #{class} ) ")
    Integer selectByCate2(@Param(value = "kingdom")String kingdom,@Param(value = "phylum") String phylum,
                         @Param(value = "class")String _class);
    @Select("select count(*) from plant where category in (select bioID from biologycategory where kingdom = #{kingdom}and phylum = #{phylum} ) ")
    Integer selectByCate3(@Param(value = "kingdom")String kingdom,@Param(value = "phylum") String phylum);
    @Select("select count(*) from plant where category in (select bioID from biologycategory where kingdom = #{kingdom}) ")
    Integer selectByCate4(@Param(value = "kingdom")String kingdom);


    @Select("select * from plant where category in (select bioID from biologycategory where kingdom = #{kingdom}and phylum = #{phylum} and class = #{class}and bio_order = #{bio_order} ) ")
    List<Plant> selectByCategory1(@Param(value = "kingdom")String kingdom,@Param(value = "phylum") String phylum,
                                 @Param(value = "class")String _class, @Param(value = "bio_order")String bio_order);

    @Select("select * from plant where category in (select bioID from biologycategory where kingdom = #{kingdom}and phylum = #{phylum} and class = #{class} ) ")
    List<Plant> selectByCategory2(@Param(value = "kingdom")String kingdom,@Param(value = "phylum") String phylum,
                                 @Param(value = "class")String _class);
    @Select("select * from plant where category in (select bioID from biologycategory where kingdom = #{kingdom}and phylum = #{phylum} and  ) ")
    List<Plant> selectByCategory3(@Param(value = "kingdom")String kingdom,@Param(value = "phylum") String phylum);
    @Select("select * from plant where category in (select bioID from biologycategory where kingdom = #{kingdom} ) ")
    List<Plant> selectByCategory4(@Param(value = "kingdom")String kingdom);

    @Select("select * from image where imageID in (select imageID from match_image where matchID = (select plantImage from plant where plantID = #{plantid}))")
    List<Image> selectImage(@Param(value = "plantid")Integer plantid);
}
