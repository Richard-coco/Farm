package cn.farm.Controller.CommonController;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("farm/common")
public class enemyController {

    @Autowired
    RestTemplate restTemplate;

    final String SERVERNAME = "http://login-Common/farm/Common/";

    //模糊搜索天敌名称
    @GetMapping(value = "enemy/foundbyname",produces = "application/json")
    public JSONObject enemyFound(@RequestParam(value = "name") String name,
                                 @RequestParam(value = "pageNum") Integer PageNum,
                                 @RequestParam(value = "pageSize") Integer PageSize){
        Map<String,Object> map  = new HashMap<>();
        map.put("name",name);
        map.put("pageNum",PageNum);
        map.put("pageSize",PageSize);
        return restTemplate.getForObject(SERVERNAME+"enemy/foundbyname?name={name}&pageNum={pageNum}&pageSize={pageSize}",JSONObject.class,map);
    }

    //根据模糊搜索得到的数据再通过id查询具体数据
    @GetMapping(value = "/enemy/info",produces = "application/json")
    public JSONObject enemyInfo(@RequestParam(value = "enemyid") Integer enemyid){
        Map<String,Object> map = new HashMap<>();
        map.put("enemyid",enemyid);
        return restTemplate.getForObject(SERVERNAME+"enemy/info?enemyid={enemyid}",JSONObject.class,map);
    }

    //联动检索天敌
    @PostMapping(value = "/enemy/foundbycate",produces = "application/json")
    public JSONObject foundbyEnemycate(@RequestParam(value = "kingdom") String kingdom,//界
                                       @RequestParam(value = "phylum") String phylum,//门
                                       @RequestParam(value = "_class") String _class,//纲
                                       @RequestParam(value = "bio_order") String bio_order,//目
                                       @RequestParam(value = "pageNum") Integer pageNum,
                                       @RequestParam(value = "pageSize") Integer pageSize){
        MultiValueMap<String,Object> param = new LinkedMultiValueMap<>();
        param.add("_class",_class);
        param.add("pageSize",pageSize);
        param.add("kingdom",kingdom);
        param.add("phylum",phylum);
        param.add("bio_order",bio_order);
        param.add("pageNum",pageNum);
        return restTemplate.postForObject(SERVERNAME+"enemy/foundbycate",param,JSONObject.class);
    }

}
