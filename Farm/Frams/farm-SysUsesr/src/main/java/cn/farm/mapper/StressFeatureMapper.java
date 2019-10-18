package cn.farm.mapper;

import cn.farm.entity.Stressfeature;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StressFeatureMapper {
    @Select("select * from stressfeature where stressType = #{stresstype} and paramFile = #{paramfile} and vectFile = #{vectfile}")
    Integer select(Stressfeature stressfeature);

    @Insert("insert into stressfeature(stressType,paramFile,vectFile) values(#{stresstype},#{paramfile},#{vectfile})")
    @Options(useGeneratedKeys = true,keyProperty = "stessid",keyColumn = "stessID")
    void add(Stressfeature stressfeature);
    @Select("select * from stressfeature where stessID = #{stessid}")
    Integer selectById(Integer stessid);
    @Update("update stressfeature set stressType = #{stresstype},paramFile = #{paramfile},vectFile = #{vectfile} where stessID = #{stessid}")
    Integer modify(Stressfeature stressfeature);

    @Select("select * from stressfeature")
    List<Stressfeature> selectAll();

    @Delete("delete from stressfeature where stessID = #{stessid}")
    void delete(Integer stessid);
}
