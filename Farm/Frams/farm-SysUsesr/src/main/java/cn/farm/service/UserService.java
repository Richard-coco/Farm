package cn.farm.service;

import cn.farm.entity.User;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface UserService {
    //1.1 查询状态码==0的待审核用户
    PageInfo<User> getUserByState(Integer state,int page, int pagesize);

    int updateUserRole(String phone, Integer state);

    int deleteUserByPhone(String phone);

    int modifyExpertMsg(User user);

    int selectExpById(Integer userid);

    /*----------------------------------------------------*/
    //1.管理员登录
    int loginSysUser(String  phone,String password);

    /*--------------------普通用户信息管理--------------------------------*/
    User queryGeneralByPhone(String phone);

    PageInfo<User> queryGeneral(Integer role,int page,int pagesize);

    int deleteGeneralByPhone(String phone);

    int modifyGeneralMsg(User user);

    Integer selectId(Integer userid);
}
