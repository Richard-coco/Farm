package cn.farm.mapper;

import cn.farm.entity.Abioticstress;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AbioticStressMapper {

    @Select("select * from abioticstress where harm = #{harm} and symptom = #{symptom} ")
    Integer selectByHarmSymptom(Abioticstress abioticstress);

    @Insert("insert into abioticstress(stressType,cropID,harm,symptom,control,symptomImage) values(#{stresstype},#{cropid},#{harm},#{symptom},#{control},#{symptomimage})")
    @Options(useGeneratedKeys = true, keyProperty = "abioticid", keyColumn = "abioticID")
    Integer add(Abioticstress abioticstress);

    @Select("select * from abioticstress where abioticID = #{abioticid}")
    Integer selectById(Integer abioticid);

    @Delete("delete from abioticstress where abioticID = #{abioticid}")
    void delete(Integer abioticid);

    @Select("select * from abioticstress")
    List<Abioticstress> selectAll();

    @Update("update abioticstress set stressType = #{stresstype},cropID = #{cropid},harm = #{harm},symptom = #{symptom},control = #{control},symptomImage = #{symptomimage} where abioticID = #{abioticid}")
    Integer modify(Abioticstress abioticstress);
}
