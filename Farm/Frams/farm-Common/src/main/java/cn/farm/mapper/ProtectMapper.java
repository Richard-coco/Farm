package cn.farm.mapper;

import cn.farm.pojo.Protection;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ProtectMapper {
        @Select("select * from protection where keyword like CONCAT('%',#{keyword},'%')")
        List<Protection> likeSelect(@Param(value = "keyword") String keyword);
}
