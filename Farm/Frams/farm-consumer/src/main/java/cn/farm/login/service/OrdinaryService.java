package cn.farm.login.service;

import cn.farm.login.exception.DefaultException;
import cn.farm.login.mapper.UserMappers;
import cn.farm.login.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdinaryService {
    private Logger logger= LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserMappers userMappers;

    public User select(String phone, String password) {

        try {
            User user =null;
//            Example example = new Example(User.class);
////            example.createCriteria().andEqualTo("phone",phone).andEqualTo("password",password);
////            List<User> users = userMapper.selectByExample(example);
            List<User> users = userMappers.selectUser(phone,password);
            if(users.size()!=0){
                user = users.get(0);//取出user存入session中
            }else{
                throw new DefaultException("用户名或密码错误");
            }
            user.setPassword("");
            return user;
        }catch (DefaultException e){
            logger.error(e.getMessage(), e);
            //同一设置为用户名或密码错误
            throw new DefaultException("Login inner error:" + e.getMessage());
        }


    }
}
