package cn.farm.service;

import cn.farm.exception.DefaultException;
import cn.farm.exception.LoginException;
import cn.farm.mapper.UserMapper;
import cn.farm.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import java.util.List;

@Service
public class OrdinaryService {
    private Logger logger= LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserMapper userMapper;

    public User select(String phone, String password) {

        try {
            User user =null;
            Example example = new Example(User.class);
            example.createCriteria().andEqualTo("phone",phone).andEqualTo("password",password);
            List<User> users = userMapper.selectByExample(example);
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
