package cn.farm.Controller;

import cn.farm.MessgaeModel.Common;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@Slf4j
public class TestController {

    final String servername = "ordinary";

    @RequestMapping("ping")
    public Common upload(HttpSession session){
        log.info("success ping " + servername);
        //据我发现 必须操作下Session响应头才会有Set-Cookie
        session.setAttribute("status","success get session");
        return new Common(200,"success ping "+servername);
    }
}
