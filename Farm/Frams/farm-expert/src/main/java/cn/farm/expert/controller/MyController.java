package cn.farm.expert.controller;

import cn.farm.expert.domain.Data;
import cn.farm.expert.domain.ExpertUser;
import cn.farm.expert.domain.JsonData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/*
* 这是测试的时候使用的一个类 与项目无关
*/
@RequestMapping("login/")
@RestController
public class MyController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("test")
    public JsonData login(HttpSession session){

        ExpertUser user = new ExpertUser();
        user.setRole(1);
        user.setUserId(1);
        user.setName("周敏");
        user.setPassword("666666");
        user.setPhone(666666);
        session.setAttribute("user",user);
        //打印日志信息
        logger.info("模拟登陆成功");
        Data data = new Data();
        data.setCode(402);
        data.setMsg("登录成功");
        return JsonData.requestError(data);

    }
}
