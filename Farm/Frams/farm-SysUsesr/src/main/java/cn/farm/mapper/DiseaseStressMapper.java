package cn.farm.mapper;

import cn.farm.entity.Diseasestress;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiseaseStressMapper {
    @Insert("insert into diseasestress(stressType,cropID,chineseName,latinName,alias,distribution,symptom,pathogeny,occurrence,control,pathoImage,symptomImage) values(#{stresstype},#{cropid},#{chinesename},#{latinname},#{alias},#{distribution},#{symptom},#{pathogeny},#{occurrence},#{control},#{pathoimage},#{symptomimage})")
    @Options(useGeneratedKeys = true,keyProperty = "diseaseid",keyColumn = "diseaseID")
    Integer add(Diseasestress diseasestress);

    @Select("select * from diseasestress where chineseName = #{chinesename}")
    Integer selectByName(Diseasestress diseasestress);


    @Select("select * from diseasestress where diseaseID = #{diseaseid}")
    Integer selectById(Integer diseaseid);

    @Delete("delete from diseasestress where diseaseID = #{diseaseid}")
    void delete(Integer diseaseid);

    @Select("select * from diseasestress")
    List<Diseasestress> selectAll();

    @Update("update diseasestress set stressType = #{stresstype},cropID = #{cropid},chineseName = #{chinesename},latinName = #{latinname},alias = #{alias},distribution = #{distribution},symptom = #{symptom},pathogeny = #{pathogeny},occurrence = #{occurrence},control = #{control},pathoImage = #{pathoimage},symptomImage = #{symptomimage} where diseaseID = #{diseaseid}")
    Integer modify(Diseasestress diseasestress);
}
