package cn.farm.mapper;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
@Repository
public interface PlantMapperSql {
    @Update("update plant set chineseName = #{chinesename},latinName = #{latinname},alias = #{alias},category = #{category},distribution = #{distribution},morphology = #{morphology},plantImage = #{plantimage},growthHabit = #{growthhabit} where plantID = #{plantid}")
    void updatePlantMsg(Integer plantid, String latinname, String chinesename, String alias, Integer category, String distribution, String morphology, Integer plantimage, String growthhabit);

    @Select("select plantID from plant where chineseName = #{chinesename}")
    Integer findId(String chinesename);

    @Update("update plant set chineseName = #{chinesename} where plantID = #{plantid}")
    void updateNameById(Integer plantid);

}
