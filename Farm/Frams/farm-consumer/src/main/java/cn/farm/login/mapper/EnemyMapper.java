package cn.farm.login.mapper;


import cn.farm.login.pojo.Enemy;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface EnemyMapper extends Mapper<Enemy> {
    @Select("select * from enemy where chineseName like CONCAT('%',#{name},'%')")
    List<Enemy> likeSelect(@Param(value = "name") String name);

    @Select("select * from enemy where enemyID =#{enemyid}")
    Enemy selectByKey(@Param(value = "enemyid") Integer enemyid);

    @Select("select count(*) from enemy where category in (select bioID from biologycategory where kingdom = #{kingdom}and phylum = #{phylum} and class = #{class}and bio_order = #{bio_order} ) ")
    Integer selectByCate1(@Param(value = "kingdom") String kingdom, @Param(value = "phylum") String phylum,
                          @Param(value = "class") String _class, @Param(value = "bio_order") String bio_order);

    @Select("select count(*) from enemy where category in (select bioID from biologycategory where kingdom = #{kingdom}and phylum = #{phylum} and class = #{class} ) ")
    Integer selectByCate2(@Param(value = "kingdom") String kingdom, @Param(value = "phylum") String phylum,
                          @Param(value = "class") String _class);
    @Select("select count(*) from enemy where category in (select bioID from biologycategory where kingdom = #{kingdom}and phylum = #{phylum} ) ")
    Integer selectByCate3(@Param(value = "kingdom") String kingdom, @Param(value = "phylum") String phylum);
    @Select("select count(*) from enemy where category in (select bioID from biologycategory where kingdom = #{kingdom}) ")
    Integer selectByCate4(@Param(value = "kingdom") String kingdom);


    @Select("select * from enemy where category in (select bioID from biologycategory where kingdom = #{kingdom}and phylum = #{phylum} and class = #{class}and bio_order = #{bio_order} ) ")
    List<Enemy> selectByCategory1(@Param(value = "kingdom") String kingdom, @Param(value = "phylum") String phylum,
                                  @Param(value = "class") String _class, @Param(value = "bio_order") String bio_order);

    @Select("select * from enemy where category in (select bioID from biologycategory where kingdom = #{kingdom}and phylum = #{phylum} and class = #{class} ) ")
    List<Enemy> selectByCategory2(@Param(value = "kingdom") String kingdom, @Param(value = "phylum") String phylum,
                                  @Param(value = "class") String _class);
    @Select("select * from enemy where category in (select bioID from biologycategory where kingdom = #{kingdom}and phylum = #{phylum} and  ) ")
    List<Enemy> selectByCategory3(@Param(value = "kingdom") String kingdom, @Param(value = "phylum") String phylum);
    @Select("select * from enemy where category in (select bioID from biologycategory where kingdom = #{kingdom} ) ")
    List<Enemy> selectByCategory4(@Param(value = "kingdom") String kingdom);
}
