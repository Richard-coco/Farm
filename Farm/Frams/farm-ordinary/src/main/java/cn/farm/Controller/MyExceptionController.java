package cn.farm.Controller;


import cn.farm.MessgaeModel.Common;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MyExceptionController {

    //noLoginException 访问noLogin
    @ResponseBody
    @RequestMapping("noLogin")
    public Common noLogin() {
        return new Common(403, "no login");
    }



}
