package cn.farm.Controller.CommonController;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("farm/common")
public class dengerController {

    final String  SERVER_NAME = "http://login-Common/farm/Common/";

    @Autowired
    RestTemplate restTemplate;

    //病害名称模糊查询
    @PostMapping(value = "/disease/foundbyname",produces = "application/json")
    public JSONObject diseaseFound(@RequestParam(value = "name") String name,
                                   @RequestParam(value = "pageNum") Integer PageNum,
                                   @RequestParam(value = "pageSize") Integer PageSize){
        MultiValueMap<String,Object> param = new LinkedMultiValueMap<>();
        param.add("name",name);
        param.add("pageNum",PageNum);
        param.add("pageSize",PageSize);
        return restTemplate.postForObject(SERVER_NAME+"disease/foundbyname",param,JSONObject.class);
    }

    //虫害名称模糊检索
    @PostMapping(value = "/pest/foundbyname",produces = "application/json")
    public JSONObject pestFound(@RequestParam(value = "name") String name,
                                @RequestParam(value = "pageNum") Integer PageNum,
                                @RequestParam(value = "pageSize") Integer PageSize){
        MultiValueMap<String,Object> param = new LinkedMultiValueMap<>();
        param.add("name",name);
        param.add("pageNum",PageNum);
        param.add("pageSize",PageSize);
        return restTemplate.postForObject(SERVER_NAME+"pest/foundbyname",param,JSONObject.class);
    }

    //虫害联动查询
    @PostMapping(value = "/pest/foundbycate",produces = "application/json")
    public JSONObject foundpestbycate( @RequestParam(value = "kingdom") String kingdom,//界
                                       @RequestParam(value = "phylum") String phylum,//门
                                       @RequestParam(value = "_class") String _class,//纲
                                       @RequestParam(value = "bio_order") String bio_order,//目
                                       @RequestParam(value = "pageNum") Integer pageNum,
                                       @RequestParam(value = "pageSize") Integer pageSize){

        MultiValueMap<String,Object> param = new LinkedMultiValueMap<>();
        param.add("pageSize",pageSize);
        param.add("kingdom",kingdom);
        param.add("phylum",phylum);
        param.add("_class",_class);
        param.add("bio_order",bio_order);
        param.add("pageNum",pageNum);
        return restTemplate.postForObject(SERVER_NAME+"pest/foundbycate",param,JSONObject.class);

    }
}
