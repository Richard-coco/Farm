package cn.farm.service;

import cn.farm.exception.DefaultException;
import cn.farm.exception.LoginException;
import cn.farm.mapper.UserMapper;
import cn.farm.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.io.File;
import java.util.List;

@Service
@Transactional
public class UserModifyService {
    private Logger logger= LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserMapper userMapper;
    public User modify(User user, String name, String nickname, String email,
                       Integer address, String postcode, String introduction) {
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userid",user.getUserid());//确定user对象
        try{
            if(name != "") {
                user.setName(name);
            }if(nickname != ""){
                user.setNickname(nickname);
            }if(email != "") {
                user.setEmail(email);
            }if(address != null) {
                user.setAddress(address);
            }if(postcode != "") {
                user.setPostcode(postcode);
            }if(introduction != "") {
                user.setIntroduction(introduction);
            }
            userMapper.updateByExample(user,example);
            //modify修改user用户

            //查询user用户，并返回给controller，最后存入session中
            Example example1 = new Example(User.class);

            List<User> list = userMapper.selectByExample(example1);
            return list.get(0);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            // 所有编译期异常转换为运行期异常
            throw new LoginException("modify inner error:" + e.getMessage());
        }

    }

    public void modifyPassword(Integer userid, String password) {
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("userid",userid);
        List<User> list = userMapper.selectByExample(example);

        User user = list.get(0);
        user.setPassword(password);
        try {
            Integer i = userMapper.updateByExample(user,example);
            if(i==0){
                throw new DefaultException("修改密码失败");
            }
            //相当于只更新一个或指定（不为null或者“”的字段）
        }catch (DefaultException e){
            throw new DefaultException("modifyPassword Error: "+e.getMessage());
        }
    }

    public void modifyPhone(Integer userid, String phone) {
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("userid",userid);
        List<User> list = userMapper.selectByExample(example);

        User user = list.get(0);
        user.setPhone(phone);
        try {
            Integer i = userMapper.updateByExample(user,example);
            if(i==0){
                throw new DefaultException("修改绑定电话失败");
            }
            //相当于只更新一个或指定（不为null或者“”的字段）
        }catch (DefaultException e){
            throw new DefaultException("modifyPassword Error: "+e.getMessage());
        }
    }

    public void ModifyPhoto(Integer userid,String photoPath) {

        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("userid",userid);
        List<User> list = userMapper.selectByExample(example);

        User user = list.get(0);
        user.setPhoto(photoPath);
        try {
            Integer i = userMapper.updateByExample(user,example);
            if(i==0){
                throw new DefaultException("修改头像失败");
            }
        }catch (DefaultException e){
            throw new DefaultException("modifyPassword Error: "+e.getMessage());
        }
    }
}
