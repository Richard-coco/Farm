package cn.farm.Controller.CommonController;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/farm/common")
public class protectController {

    @Autowired
    RestTemplate restTemplate;

    final String SERVERNAME = "http://login-common/farm/common/";

    //==========植保（标题模糊搜索）搜索===========
    @GetMapping(value = "/protect/foundbykey",produces = "application/json")
    public JSONObject FoundByProtect(@RequestParam(value = "keyword")String keyword,
                                     @RequestParam(value = "PageNum")Integer PageNum,
                                     @RequestParam(value = "PageSize")Integer PageSize){
        Map<String,Object> map = new HashMap<>();
        map.put("keyword",keyword);
        map.put("PageNum",PageNum);
        map.put("PageSize",PageSize);
        return restTemplate.getForObject(SERVERNAME+"protect/foundbykey?keyword={keyword}&PageNum={PageNum}&PageSize={PageSize}",JSONObject.class,map);
    }

    //咨询信息模糊查询
    @GetMapping(value = "/message/foundbykey",produces = "application/json")
    public JSONObject Messagefoundbytitle( @RequestParam(value = "keyword")String keyword,
                                           @RequestParam(value = "PageNum")Integer PageNum,
                                           @RequestParam(value = "PageSize")Integer PageSize){
        Map<String,Object> map = new HashMap<>();
        map.put("PageNum",PageNum);
        map.put("keyword",keyword);
        map.put("PageSize",PageSize);
        return restTemplate.getForObject(SERVERNAME+"message/foundbykey?keyword={keyword}&PageNum={PageNum}&PageSize={PageSize}",JSONObject.class,map);
    }
}
