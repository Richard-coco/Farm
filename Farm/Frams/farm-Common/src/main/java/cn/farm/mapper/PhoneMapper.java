package cn.farm.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface PhoneMapper {
    @Select("select count(*) from user where phone = #{phone}")
    Integer foundPhone(@Param(value = "phone") String phone);
}
