package cn.farm.Controller.ExpertController;

import cn.farm.Aop.IsLogin;
import cn.farm.Util.FileUtil;
import cn.farm.Util.UserUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("farm/expert/rule")
public class RuleController {

    final String SERVER_NAME = "http://farm-Expert/farm/expert/rule/";

    @Autowired
    RestTemplate restTemplate;

    /**
     * 查询诊断规则
     *
     * @return
     */
    @IsLogin
    @GetMapping(value = "find", produces = "application/json")
    public JSONObject findRule(HttpSession session, @RequestParam(value = "pagesize", defaultValue = "3") int pageSize,
                               @RequestParam(value = "pagenum", defaultValue = "1") int pageNum) {

        Map<String, Object> map = new HashMap<>();
        map.put("pagesize", pageSize);
        map.put("pagenum", pageNum);
        map.put("userid", UserUtil.getUserID(session));
        return restTemplate.getForObject(SERVER_NAME + "find?pagesize={pagesize}&pagenum={pagenum}&userid{userid}", JSONObject.class, map);
    }

    /**
     * 添加诊断规则
     *
     * @param ruleName
     * @param stressType
     * @param cropId
     * @param file
     * @param description
     * @return
     */
    @IsLogin
    @PostMapping(value = "save", produces = "application/json")
    public JSONObject saveRule(HttpSession session, @RequestParam(value = "rulename", required = true) String ruleName,
                               @RequestParam(value = "stresstype", required = true) int stressType,
                               @RequestParam(value = "cropid", required = true) int cropId,
                               @RequestParam(value = "rulefile", required = false) MultipartFile file,
                               @RequestParam(value = "description", required = false) String description) {
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add("userid", UserUtil.getUserID(session));
        param.add("rulename", ruleName);
        param.add("stresstype", stressType);
        param.add("cropid", cropId);
        param.add("description", description);
        if (file != null) {
            try {
                param.add("rulefile", FileUtil.multipartFileToFileSystemResource(file));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return restTemplate.postForObject(SERVER_NAME + "save", param, JSONObject.class);
    }

    /**
     * 删除诊断规则
     * @param ruleId
     * @return
     */
    @IsLogin
    @GetMapping(value = "remove",produces = "application/json")
    public JSONObject removeRule(HttpSession session , @RequestParam(value = "ruleid",required = true) int ruleId){
        Map<String,Object> map = new HashMap();
        map.put("ruleid",ruleId);
        map.put("userid",UserUtil.getUserID(session));
        return restTemplate.getForObject(SERVER_NAME+"remove?ruleid={ruleid}&userid={userid}",JSONObject.class,map);
    }

    /**
     * 更新诊断规则
     * rule
     * @return
     */
    @IsLogin
    @PostMapping(value = "update",produces = "application/json")
    public JSONObject updateRule(HttpSession session,@RequestParam(value = "ruleid",required = true) int ruleId ,
                           @RequestParam(value = "rulename",required = false) String ruleName,
                           @RequestParam(value = "stresstype",required = false)int stressType,
                           @RequestParam(value = "cropid",required = false)int cropId,
                           @RequestParam(value = "rulefile",required = false)MultipartFile file,
                           @RequestParam(value = "description",required = false)String description
                           ){
        MultiValueMap<String,Object> param = new LinkedMultiValueMap<>();
        param.add("ruleid",ruleId);
        param.add("rulename",ruleName);
        param.add("stresstype",stressType);
        param.add("cropid",cropId);
        param.add("rulefile",file);
        param.add("description",description);
        param.add("userid",UserUtil.getUserID(session));
        return restTemplate.postForObject(SERVER_NAME+"update",param,JSONObject.class);
    }
}
