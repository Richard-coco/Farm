package cn.farm.Mapper;


import cn.farm.Entity.MatchImage;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MatchImageMapper {

    @Options(useGeneratedKeys = true,keyProperty = "matchID")
    @Insert("insert into match_image (imageID,matchinfo) values(#{imageID},#{matchinfo})")
    void insert(MatchImage matchImage);

    @Insert("insert into match_image (matchID,imageID,matchinfo) values(#{matchID},#{imageID},#{matchinfo}) ")
    void insertWithMatchID(MatchImage matchImage);

    @Select("select imageID from match_image where matchID = #{matchID}")
    List<Integer> selectByMatchID(int matchID);

    @Select("select count(*) from match_image where matchID = #{matchID}")
    int selectCountByMatchID(int matchID);

    @Delete("delete from match_image where imageID = #{imageID} ")
    void deleteByImageID(int imageID);

    @Delete("delete from match_image where matchID = #{matchID}")
    void deleteByMatchID(int matchID);
}
