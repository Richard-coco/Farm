package cn.farm.service.impl;

import cn.farm.entity.User;
import cn.farm.exception.DataException;
import cn.farm.mapper.UserMapper;
import cn.farm.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public PageInfo<User> getUserByState(Integer state,int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        List<User> users = userMapper.getUserByState(state);
        PageInfo<User> pageInfoUserList = new PageInfo<User>(users);
        return pageInfoUserList;
    }

    @Override
    public int updateUserRole(String phone, Integer state) {
      return userMapper.updateUserRole(phone,state);
    }


    @Override
    public int deleteUserByPhone(String phone) {
        // 先判断数据库中是否需存在该用户
        User user = userMapper.selectGeneralByPhone(phone);
        if (user != null){
            return userMapper.deleteUserByPhone(phone);
        }
        else {
            return 0;
        }
    }

    @Override
    public int modifyExpertMsg(User user) {
        return userMapper.modifyExpertMsg(user);
    }

    @Override
    public int selectExpById(Integer userid) {
        return userMapper.selectExpById(userid);
    }

    /*---------------------------------------------------------------------*/
    //1.管理员登录Service实现
    @Override
    public int loginSysUser(String phone, String password) {
        return userMapper.loginSysUser(phone,password);
    }

    @Override
    public User queryGeneralByPhone(String phone) {
         return userMapper.selectGeneralByPhone(phone);

    }

    @Override
    public PageInfo<User> queryGeneral(Integer role,int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        List<User> users = userMapper.selectGeneral(role);
        PageInfo<User> pageInfoGenerUserList = new PageInfo<User>(users);
        return pageInfoGenerUserList;
    }

    @Override
    public int deleteGeneralByPhone(String phone) {
        return userMapper.deleteGeneralByPhone(phone);
    }

    @Override
    public int modifyGeneralMsg(User user) {
        return userMapper.updateGeneral(user);
    }

    @Override
    public Integer selectId(Integer userid) {
        Integer id = userMapper.selectId(userid);
        if (id != null){
            return id;
        }else {
            throw new DataException("400","该用户不存在");
        }
    }


}
