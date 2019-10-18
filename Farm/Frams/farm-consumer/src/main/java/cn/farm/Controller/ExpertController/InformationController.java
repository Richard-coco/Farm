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

@RequestMapping("farm/expert/information")
@RestController
public class InformationController {


    final String  SERVER_NAME = "http://farm-expert/farm/expert/information/";

    @Autowired
    RestTemplate restTemplate;

    /**
     * 查询自己录入的胁迫情报
     * @return
     */
    @IsLogin
    @GetMapping(value = "find",produces = "application/problem+json")
    public JSONObject findInformation(HttpSession session,
                                      @RequestParam(value = "pagesize",defaultValue = "3") int  pageSize,
                                      @RequestParam(value = "pagenum",defaultValue = "1")int pageNum){
        Map<String,Object> map = new HashMap<>();
        map.put("userid", UserUtil.getUserID(session));
        map.put("pagesize",pageSize);
        map.put("pagenum",pageNum);
        return restTemplate.getForObject(SERVER_NAME+"find",JSONObject.class,map);
    }

    /**
     * 添加胁迫情报
     * @param
     * @return 添加成功数
     */
    @IsLogin
    @PostMapping(value = "add",produces = "application/json")
    public JSONObject saveInformation(HttpSession session,@RequestParam(value = "title",required = false,defaultValue = "无标题")String  title1,
                                @RequestParam(value = "file",required = false) MultipartFile multipartFile,
                                @RequestParam(value = "content",required = false,defaultValue = "内容为空")String content1){
        MultiValueMap<String,Object> param = new LinkedMultiValueMap<>();
        param.add("title",title1);
        param.add("content",content1);
        param.add("userid",UserUtil.getUserID(session));
        try {
            if(multipartFile != null){
                param.add("file", FileUtil.multipartFileToFileSystemResource(multipartFile));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return restTemplate.postForObject(SERVER_NAME+"add",param,JSONObject.class);
    }


    /**
     * 删除自己录入的胁迫情报
     * @param infoId
     * @return
     */
    @IsLogin
    @GetMapping(value = "remove",produces = "application/json")
    public JSONObject removeInformation(HttpSession session, @RequestParam(value = "infoid",required = true) int infoId){
        Map<String,Object> map = new HashMap<>();
        map.put("infoid",infoId);
        map.put("",UserUtil.getUserID(session));
        return restTemplate.getForObject(SERVER_NAME+"remove",JSONObject.class,map);
    }

    /**
     * 更新录入的胁迫情报
     * @param title
     * @param multipartFile
     * @param content
     * @return
     */
    @IsLogin
    @PostMapping(value = "update",produces = "application/json")
    public JSONObject updateInformation(HttpSession session , @RequestParam(value = "title",required = false,defaultValue = "无标题")String  title,
                                   @RequestParam(value = "file",required = false) MultipartFile multipartFile,
                                   @RequestParam(value = "content",required = false,defaultValue = "内容为空")String content){

        MultiValueMap<String,Object> param = new LinkedMultiValueMap<>();
        param.add("title",title);
        param.add("content",title);
        param.add("userid",UserUtil.getUserID(session));
        if(multipartFile != null ){
            try {
                param.add("file",FileUtil.multipartFileToFileSystemResource(multipartFile));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return restTemplate.postForObject(SERVER_NAME+"update",param,JSONObject.class);
    }


}
