package cn.farm.expert.mapper;

import cn.farm.expert.domain.MatchImage;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface MatchImageMapper {

    @Select("select matchId from match_image where imageId = #{imageId}")
    int findMatchId(@Param("imageId") int imageId);

    @Insert("insert into `match_image` values (#{matchId},#{imageId},#{matchinfo}); ")
    int add(MatchImage matchImage);


}
