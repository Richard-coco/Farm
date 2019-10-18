package cn.farm.mapper;

import cn.farm.entity.Image;
import cn.farm.entity.Match_Image;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;


@Repository
public interface MatchImageMapperSql {
    @Insert("insert into match_image(imageID,matchinfo) values(#{imageid},#{matchinfo})")
    @Options(useGeneratedKeys = true,keyColumn = "matchID",keyProperty = "matchid")//实例对象中主键的属性名keyProperty,数据库中的字段名keyColumn
    int insert(Match_Image matchImage);
    @Insert("insert into match_image(matchID,imageid,matchinfo) values(#{matchid},#{imageid},#{matchinfo})")
    int insertWithPrimaryKey(Match_Image matchImage);

    @Delete("delete from match_image where matchID = #{matchid}")
    void delete(Integer matchid);

    @Update("update match_image set imageFile = #{imagefile} where matchID = #{matchid}")
    void update(Match_Image matchImage);

    @Select("selelct * from match_image where matchID = #{matchid}")
    void select(Integer matchid);
}
