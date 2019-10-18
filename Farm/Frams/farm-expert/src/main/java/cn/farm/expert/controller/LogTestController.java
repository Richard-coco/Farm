package cn.farm.expert.controller;

import cn.farm.expert.domain.Data;
import cn.farm.expert.domain.JsonData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
* 这是一个无用类
*/
@RestController
@RequestMapping("/user/expert")
public class LogTestController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("log")
    public Object testLog(){

        logger.debug("this is debug level");
        logger.info("this is info level ");
        logger.warn("this is warn level ");
        logger.error("this is error level");
        Data data = new Data();
        data.setCode(1);
        return JsonData.creatSuccess(data);
    }

}
