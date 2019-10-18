package cn.farm.Controller.OrdinaryController;


import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("farm/ordinary")
public class SolrController {

    String path = "http://farm-ordinary/farm/ordinary/";


    @Autowired
    RestTemplate restTemplate;

    //@Cacheable(value = "solr",keyGenerator = "solrGenerator")
    @RequestMapping("solr")
    public JSONObject selectSolr(String context, String start, String rows){
        Map<String,String> map = new HashMap<>();
        map.put("context",context);
        if(start == null){
            map.put("start","0");
        }else{
            map.put("start",start);
        }

        if(rows == null){
            map.put("rows","10");
        }else{
            map.put("rows",rows);
        }

        return restTemplate.getForObject(path+"solr?context={context}&start={start}&rows={rows}", JSONObject.class,map);
    }

}
