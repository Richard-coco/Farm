package cn.farm.Controller.ExceptionController;

import cn.farm.MessageModel.Common;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExceptionController {

    //noLoginException 访问noLogin
    @ResponseBody
    @RequestMapping("noLogin")
    public Common noLogin() {
        return new Common(406, "no login");
    }

    @RequestMapping("unauthorized")
    public Common unauthorized(){
        return new Common(401,"unauthorized");
    }
}
