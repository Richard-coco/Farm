package cn.farm.mapper;

import cn.farm.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import java.util.List;

/*
* 1.用户信息管理模块
* */
@Repository
public interface UserMapper {
    /*
    * 1.1 筛选a待升级为专家用户的信息
    * */
    @Select("select * from user where state = #{state}")
    List<User> getUserByState(Integer state);  //直接查询状态码=0的用户

    @Delete("delete from user where phone = #{phone} and role = 2 ")
    int deleteUserByPhone(String phone);

    @Update("update user set state = #{state} where phone = #{phone} ")
    int updateUserRole(String phone,Integer state);

    //修改专家用户信息
    @Update("update user set password = #{password},name = #{name},nickname = #{nickname},email = #{email},postcode = #{postcode},photo = #{photo},introduction = #{introduction} where phone = #{phone}")
    int modifyExpertMsg(User user);

    @Select("select * from user where userID = #{userID}")
    int selectExpById(Integer userid);

    /*---------------------------------------------------------------------*/

    //1.管理员登录
    @Select("select * from user where phone = #{phone} and password = #{password} ")
    Integer loginSysUser(@Param("phone") String  phone,@Param("password") String password);


    /*---------------------------普通用户信息管理————————————————*/
    @Select("select * from user where phone = #{phone} and role = 3")
    User selectGeneralByPhone(String phone);

    @Select("select * from user where role = #{role}")
      List<User> selectGeneral(Integer role);

    @Delete("delete from user where phone = #{phone} and role = 3")
    int deleteGeneralByPhone(String phone);

    @Update("update user set userID = #{userid},password = #{password},name = #{name},nickname = #{nickname},email = #{email},postcode = #{postcode},photo = #{photo},introduction = #{introduction} where phone = #{phone}")
    int updateGeneral(User user);

    @Select("select userID from user where userID = #{userid}")
    Integer selectId(Integer userid);

}

