package cn.farm.service;

import cn.farm.exception.PhoneException;
import cn.farm.mapper.PhoneMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhoneService {

    @Autowired
    private PhoneMapper phoneMapper;


    public void foundPhone(String phone) {
        try {
            Integer use = phoneMapper.foundPhone(phone);
            if(use >=1 ){
                throw new PhoneException("该号码已被使用，请重新输入");
            }
        }catch (PhoneException p){
            throw new PhoneException("PhoneException inner error "+ p.getMessage());
        }

    }

    public void foundUsedPhone(String phone) {
        try {
            Integer use = phoneMapper.foundPhone(phone);
            if(use == 0 ){
                throw new PhoneException("该号码未被绑定，请先注册");
            }
        }catch (PhoneException p){
            throw new PhoneException("PhoneException inner error "+ p.getMessage());
        }
    }
}
