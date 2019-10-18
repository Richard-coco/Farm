package cn.farm.mapper;

import cn.farm.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMappers {

    @Select("select * from user where phone = #{phone}")
    List<User> foundPhone(@Param(value = "phone") String phone);
}
