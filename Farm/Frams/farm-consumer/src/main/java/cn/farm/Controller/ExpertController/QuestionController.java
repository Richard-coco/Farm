package cn.farm.Controller.ExpertController;

import cn.farm.Aop.IsLogin;
import cn.farm.Util.UserUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/farm/expert/")
@RestController
public class QuestionController {

    final String SERVER_NAME = "http://farm-xpert/farm/expert/";

    @Autowired
    RestTemplate restTemplate;

    @IsLogin
    @GetMapping(value = "answerbyexpertid/find",produces = "application/problem+json;charset=UTF-8")
    public JSONObject findAnswerByExpertId(HttpSession session,
                                           @RequestParam(value = "pagesize",defaultValue = "3") int  pageSize,
                                           @RequestParam(value = "pagenum",defaultValue = "1")int pageNum){
        Map<String,Object> map = new HashMap<>();
        map.put("pagesize",pageSize);
        map.put("pagenum",pageNum);
        map.put("userid", UserUtil.getUserID(session));
        return restTemplate.getForObject(SERVER_NAME+"answerbyexpertid/find?pagesize",JSONObject.class,map);
    }

    /**
     * 回答问题
     * @param questId
     * @param content
     * @return
     */
    @IsLogin
    @PostMapping(value = "answer/save",produces = "application/json")
    public JSONObject saveAnswer(HttpSession session,
                                 @RequestParam(value = "questid",required = true)int questId,
                                 @RequestParam(value = "content",required = true)String content,
                                 @RequestParam(value = "answerid",required = true) int answerId){
        MultiValueMap<String,Object> param = new LinkedMultiValueMap<>();
        param.add("questid",questId);
        param.add("content",content);
        param.add("answerid",answerId);
        param.add("userid",UserUtil.getUserID(session));
        return restTemplate.postForObject(SERVER_NAME+"answer/save",param,JSONObject.class);
    }

    /**
     *修改问题
     * answer
     * @return
     */
    @IsLogin
    @PostMapping(value = "answer/update",produces = "application/json")
    public JSONObject updateAnswer(HttpSession session,@RequestParam(value = "questid",required = true)int questId,
                             @RequestParam(value = "content",required = false)String content,
                             @RequestParam(value = "answerid",required = true) int answerId){

        MultiValueMap<String,Object> param = new LinkedMultiValueMap<>();
        param.add("questid",questId);
        param.add("content",content);
        param.add("answerid",answerId);
        param.add("userid",UserUtil.getUserID(session));
        return restTemplate.postForObject(SERVER_NAME+"answer/update",param,JSONObject.class);
    }

    /**
     * 删除问题
     * @param answerId
     * @return
     */
    @IsLogin
    @GetMapping(value = "answer/remove",produces = "application/json")
    public JSONObject removeAnswer(HttpSession session ,@RequestParam("answerid") int answerId){
        Map<String,Object> map = new HashMap<>();
        map.put("answerid",answerId);
        map.put("userid",UserUtil.getUserID(session));
        return restTemplate.getForObject(SERVER_NAME+"answer/remove",JSONObject.class,map);
    }
}
