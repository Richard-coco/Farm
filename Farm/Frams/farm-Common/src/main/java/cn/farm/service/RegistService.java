package cn.farm.service;

import cn.farm.exception.DefaultException;
import cn.farm.mapper.UserMapper;
import cn.farm.mapper.UserMappers;
import cn.farm.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistService {
    private Logger logger= LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserMappers userMappers;

    public void regist(String name, String password, String nickname, String phone, String email, Integer address, String postcode, String introduction, Integer state, Integer role) {
        try {
            User user = new User(name,password,nickname,phone,email,address,postcode,introduction,state,role);
            //判断用户电话是否重复
            List<User> users = userMappers.foundPhone(phone);
            if(!users.isEmpty()){
                throw new DefaultException("phone重复");
            }

            Integer i = userMapper.insert(user);
            if(i ==0 ){
                throw new DefaultException("注册失败");
            }
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            // 所有编译期异常转换为运行期异常
            throw new DefaultException("Regist inner error:" + e.getMessage());
        }

    }


    //专家用户注册
    public void ExpertRegist(String name, String password, String nickname, String phone, String email, Integer address, String postcode, String introduction, Integer state, Integer role) {
        User user = new User(name,password,nickname,phone,email,address,postcode,introduction,state,role);
        try {
            //判断用户电话是否重复
            List<User> users = userMappers.foundPhone(phone);
            if(!users.isEmpty()){
                throw new DefaultException("phone重复");
            }
            Integer i = userMapper.insert(user);
            if(i ==0 ){
                throw new DefaultException("注册失败");
            }
        }catch (DefaultException e){
            throw new DefaultException("ExpertRegist inner error:" + e.getMessage());
        }
    }
}
