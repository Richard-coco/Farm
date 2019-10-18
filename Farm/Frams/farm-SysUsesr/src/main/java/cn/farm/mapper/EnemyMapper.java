package cn.farm.mapper;

import cn.farm.entity.Enemy;
import cn.farm.utils.CommonResult;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnemyMapper {
    @Insert("insert into enemy(enemyID,chineseName,latinName,alias,category,distribution,morphology,imageID,lifeHabit) values(#{enemyid},#{chinesename},#{latinname},#{alias},#{category},#{distribution},#{morphology},#{imageid},#{lifehabit})")
    @Options(useGeneratedKeys = true, keyProperty = "enemyid", keyColumn = "enemyID")
    int insert(Enemy enemy);

    @Delete("delete from enemy where enemyID = #{enemyid}")
    int delete(Integer enemyid);

    @Select("select * from enemy where enemyID = #{enemyid}")
    Integer selectById(Integer enemyid);

    @Select("select * from enemy")
    List<Enemy> selectAll();

    @Update("update enemy set chineseName = #{chinesename},latinName = #{latinname},alias = #{alias},category = #{category},distribution = #{distribution},morphology = #{morphology},imageID = #{imageid},lifeHabit = #{lifehabit} where enemyID = #{enemyid}")
    void update(Enemy enemy);
}
