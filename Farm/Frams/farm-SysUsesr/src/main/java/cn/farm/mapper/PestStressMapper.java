package cn.farm.mapper;
import cn.farm.entity.Peststress;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PestStressMapper {
    @Insert("insert into peststress(stressType,cropID,chineseName,latinName,alias,category,distribution,morphology,lifeHabit,damage,control,pestImage,syptomImage) values(#{stresstype},#{cropid},#{chinesename},#{latinname},#{alias},#{category},#{distribution},#{morphology},#{lifehabit},#{damage},#{control},#{pestimage},#{syptomimage})")
    @Options(useGeneratedKeys = true, keyProperty = "pestid", keyColumn = "pestID")
    Integer add(Peststress peststress);

    @Select("select * from peststress where chineseName = #{chinesename}")
    Integer selectByName(Peststress peststress);

    @Select("select * from peststress where pestID = #{pestid}")
    Integer selectById(Integer pestid);

    @Delete("delete from peststress where pestID = #{pestid}")
    void delete(Integer pestid);

    @Select("select * from peststress")
    List<Peststress> selectAll();

    @Update("update peststress set stressType = #{stresstype},cropID = #{cropid},chineseName = #{chinesename},latinName = #{latinname},alias = #{alias},category = #{category},distribution = #{distribution},morphology = #{morphology},lifeHabit = #{lifehabit},damage = #{damage},control = #{control},pestImage = #{pestimage},syptomImage = #{syptomimage} where pestID = #{pestid}")
    Integer modify(Peststress peststress);
}
