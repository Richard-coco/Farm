package cn.farm.login.mapper;

import cn.farm.login.pojo.Message;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MessagetMapper {
    @Select("select * from message where keyword like CONCAT('%',#{keyword},'%')")
    List<Message> likeSelect(@Param(value = "keyword") String keyword);
}
