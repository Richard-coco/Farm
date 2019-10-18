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
public class plantController {

    @Autowired
    RestTemplate restTemplate;

    final String SERVER_NAME = "http://login-Common/farm/Common/";

    //模糊搜索crop
    //只返回chinesename，plantID，norphology三个属性
    @GetMapping(value = "/plant/foundbyname",produces = "application/json")
    public JSONObject cropFound(@RequestParam(value = "name") String name,
                                @RequestParam(value = "pageNum") Integer PageNum,
                                @RequestParam(value = "pageSize") Integer PageSize){
        Map<String,Object> map = new HashMap<>();
        map.put("name",name);
        map.put("pageNum",PageNum);
        map.put("pageSize",PageSize);
        return restTemplate.getForObject(SERVER_NAME+"plant/foundbyname?name={name}&pageNum={pageNum}&pageSize={pageSize}",JSONObject.class,map);
    }

    //根据模糊搜索得到的数据再通过id查询具体数据
    @GetMapping(value = "/plant/info",produces = "application/json")
    public JSONObject plantInfo(@RequestParam(value = "plantid") Integer plantid){
        return restTemplate.getForObject(SERVER_NAME+"plant/info?plantid="+plantid,JSONObject.class);
    }

    //根据作物分类查询所有值
    @PostMapping(value = "/plant/foundbycate",produces = "application/json")
    public JSONObject foundbycate(@RequestParam(value = "kingdom") String kingdom,//界
                                  @RequestParam(value = "phylum") String phylum,//门
                                  @RequestParam(value = "_class") String _class,//纲
                                  @RequestParam(value = "bio_order") String bio_order,//目
                                  @RequestParam(value = "pageNum") Integer pageNum,
                                  @RequestParam(value = "pageSize") Integer pageSize){

        MultiValueMap<String,Object> param = new LinkedMultiValueMap<>();
        param.add("kingdom",kingdom);
        param.add("phylum",phylum);
        param.add("_class",_class);
        param.add("bio_order",bio_order);
        param.add("pageNum",pageNum);
        param.add("pageSize",pageSize);
        return restTemplate.postForObject(SERVER_NAME+"plant/foundbycate",param,JSONObject.class);
    }

    //====================级联查询界门纲目的所属关系=========================
    @PostMapping(value = "/category/found",produces = "application/json")
    public JSONObject foundCategory( @RequestParam(value = "type")String type,
                                     @RequestParam(value = "value")String value){
        MultiValueMap<String,Object> param = new LinkedMultiValueMap<>();
        param.add("type",type);
        param.add("value",value);
        return restTemplate.postForObject(SERVER_NAME+"category/found",param,JSONObject.class);
    }
}
