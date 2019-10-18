package cn.farm.Controller.OrdinaryController;

import cn.farm.Aop.IsLogin;
import cn.farm.Entity.Ordinary.Question;
import cn.farm.Util.FileUtil;
import cn.farm.Util.UserUtil;
import cn.farm.login.pojo.User;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
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
@RequestMapping("farm/ordinary/quest")
@Slf4j
public class QuestController {

    @Autowired
    RestTemplate restTemplate;

    public final String SERVER_NAME = "http://farm-ordinary/farm/ordinary/quest/";

    //查询本人的咨询
    @IsLogin
    @GetMapping(value = "select")
    public JSONObject selectByUserID(HttpSession session, @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                 @RequestParam(value = "pageSize",required = false,defaultValue = "2")int pageSize){
        Map<String,Integer> map = new HashMap();
        map.put("page",page);
        map.put("pageSize",pageSize);
        map.put("userID",UserUtil.getUserID(session));
        return restTemplate.getForObject(SERVER_NAME+"select?userID={userID}&page={page}&pageSize={pageSize}", JSONObject.class,map);
    }


    //查询所有咨询
    @GetMapping(value = "selectAll")
    public JSONObject selectAll(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                @RequestParam(value = "pageSize",required = false,defaultValue = "2")int pageSize){
        Map<String,Integer> map = new HashMap();
        map.put("page",page);
        map.put("pageSize",pageSize);
        return restTemplate.getForObject(SERVER_NAME+"selectAll?page={page}&pageSize={pageSize}", JSONObject.class,map);
    }


    //添加咨询
    @IsLogin
    @PostMapping("insert")
    public JSONObject insert(HttpSession session, Question question, @RequestParam(required = false,value ="file1") MultipartFile file1,
                             @RequestParam(required = false,value ="file2") MultipartFile file2, @RequestParam(required = false,value ="file3") MultipartFile file3) {

        User user = (User) session.getAttribute("user");
        question.setUserID(user.getUserid());
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        try {
            if(file1 != null){
                param.add("file1", FileUtil.multipartFileToFileSystemResource(file1));
            }
            if(file2 != null){
                param.add("file2", FileUtil.multipartFileToFileSystemResource(file2));
            }
            if(file3 != null){
                param.add("file3", FileUtil.multipartFileToFileSystemResource(file3));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        param.add("title", question.getTitle());
        param.add("content", question.getContent());
        param.add("domain", question.getDomain());
        param.add("userID", question.getUserID());
        return restTemplate.postForObject(SERVER_NAME+"insert",param,JSONObject.class);
    }


    //咨询修改 文字部分
    @IsLogin
    @PostMapping("update")
    public JSONObject update(HttpSession session, String title, String content,int questID){
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add("title",title);
        param.add("content",content);
        param.add("questID",questID);
        param.add("userID",UserUtil.getUserID(session));
        return restTemplate.postForObject(SERVER_NAME+"update",param,JSONObject.class);
    }

    /*
    修改图片
     */

    //添加图片
    @IsLogin
    @RequestMapping("insertImage")
    public String insertImage( HttpSession session,@RequestParam(required = false,value ="file") MultipartFile file,
                               @RequestParam("matchID") int matchID, @RequestParam(value = "questID")int questID){

        FileSystemResource fileSystemResource = null;
        try {
            fileSystemResource = FileUtil.multipartFileToFileSystemResource(file);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("consumer: multipartFileToFileSystemResource(file) IO Exception");
        }

        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add("file", fileSystemResource);
        param.add("matchID", matchID);
        param.add("questID", questID);
        param.add("userID", UserUtil.getUserID(session));
        return restTemplate.postForObject(SERVER_NAME+"insertImage",param,String.class);
    }


    //删除图片
    @IsLogin
    @GetMapping("deleteImage")
    public JSONObject deleteImage(HttpSession session, @RequestParam("imageID") int imageID , @RequestParam("questID") int questID){
        Map<String,Integer> map = new HashMap();
        map.put("imageID",imageID);
        map.put("questID",questID);
        map.put("userID",UserUtil.getUserID(session));
        return restTemplate.getForObject(SERVER_NAME+"deleteImage?questID={questID}&imageID={imageID}&userID={userID}",JSONObject.class,map);
    }


    //咨询删除
    @IsLogin
    @GetMapping("delete")
    public JSONObject delete(HttpSession session, int questID){
        Map<String,Integer> map = new HashMap();
        map.put("questID",questID);
        map.put("userID",UserUtil.getUserID(session));
        return restTemplate.getForObject(SERVER_NAME+"delete?questID={questID}&userID={userID}",JSONObject.class,map);
    }


}
