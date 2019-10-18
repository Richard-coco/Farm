package cn.farm.login.mapper;

import cn.farm.login.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface UserMappers {

    @Select("select * from user where phone = #{phone}")
    List<User> foundPhone(@Param(value = "phone") String phone);
    @Select("select * from user where phone = #{phone} and password = #{password}")
    List<User> selectUser(@Param(value = "phone")String phone, @Param(value = "password")String password);
    @Insert("insert into user (name,password,nickname,phone,email,address,postcode,introduction,state,role) values (#{name}," +
            "#{password},#{nickname},#{phone},#{email},#{address},#{postcode},#{introduction},#{state},#{role})")
    Integer insert(String name, String password, String nickname, String phone, String email,
                   Integer address, String postcode, String introduction, Integer state, Integer role);
    @Update("update user set name = #{name},nickname = #{nickname},email=#{email},address=#{address},postcode=#{postcode},introduction=#{introduction} where userID=#{userid}")
    Integer updateMessage(String name, String nickname, String email, Integer address, String postcode, String introduction, Integer userid);

    @Select("select * from user where userID = #{userid}")
    List<User> foundUserId(Integer userid);

    @Update("update user set password = #{password} where userID = #{userid}")
    Integer updatePassword(Integer userid,String password);
    @Update("update user set phone = #{phone} where userID = #{userid}")
    Integer updatePhone(User user);
    @Update("update user set photo = #{photo} where userID = #{userid}")
    Integer updatePhoto(User user);

    @Select("select * from user where phone = #{phone}")
    List<User> selectByPhone(String phone);
}
