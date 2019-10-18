package cn.farm.login.mapper;

import cn.farm.login.pojo.TpRegion2;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TpMapper{

    @Select("select * from tp_region2 where level = #{level} and parent_id = #{parentId}")
    List<TpRegion2> select(Integer level, Integer parentId);
}
