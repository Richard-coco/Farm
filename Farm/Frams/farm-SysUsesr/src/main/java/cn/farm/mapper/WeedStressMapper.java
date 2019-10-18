package cn.farm.mapper;

import cn.farm.entity.Weedstress;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface WeedStressMapper {

    @Insert("insert into weedstress(stressType,cropID,category,harm,symptom,control,symptomImage) values(#{stresstype},#{cropid},#{category},#{harm},#{symptom},#{control},#{symptomimage})")
    @Options(useGeneratedKeys = true,keyProperty = "weedid",keyColumn = "weedID")
    Integer add(Weedstress weedstress);

    @Select("select * from weedstress where harm = #{harm} and symptom = #{symptom}")
    Integer selectByHarmSymptom(Weedstress weedstress);

    @Select("select * from weedstress where weedID = #{weedid}")
    Integer selectById(Integer weedid);

    @Delete("delete from weedstress where weedID = #{weedid}")
    void delete(Integer weedid);

    @Select("select * from weedstress")
    List<Weedstress> selectAll();

    @Update("update weedstress set stressType = #{stresstype},cropID = #{cropid},category = #{category},harm = #{harm},symptom = #{symptom},control = #{control},symptomImage = #{symptomimage} where weedID = #{weedid}")
    Integer modify(Weedstress weedstress);
}
