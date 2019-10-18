package cn.farm.Controller.OrdinaryController;

import cn.farm.Aop.IsLogin;
import cn.farm.Entity.Ordinary.Information;
import cn.farm.Util.FileUtil;
import cn.farm.Util.UserUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
@Slf4j
@RequestMapping("farm/ordinary/info")
public class InforController {

    public final String SERVER_NAME = "http://farm-ordinary/farm/ordinary/info/";

    @Autowired
    RestTemplate restTemplate;

    @PostMapping("insert")
    @IsLogin
    public JSONObject insert(HttpSession session , Information information ,
                             @RequestParam(required = false,value ="image1") MultipartFile image1,
                             @RequestParam(required = false,value ="image2") MultipartFile image2,
                             @RequestParam(required = false,value ="image3") MultipartFile image3,
                             @RequestParam(required = false,value ="video") MultipartFile video1){

        MultiValueMap<String,Object> map = new LinkedMultiValueMap<>();
        map.add("title",information.getTitle());
        map.add("content",information.getContent());
        map.add("location",information.getLocation());
        map.add("author", UserUtil.getUserID(session));
        try {
            if(image1 != null){
                map.add("image1",FileUtil.multipartFileToFileSystemResource(image1));
            }
            if(image2 != null){
                map.add("image1",FileUtil.multipartFileToFileSystemResource(image2));
            }
            if(image3 != null){
                map.add("image1",FileUtil.multipartFileToFileSystemResource(image3));
            }
            if(video1 != null){
                map.add("video",FileUtil.multipartFileToFileSystemResource(video1));
            }

        } catch (IOException e) {
            e.printStackTrace();
            log.error("IO Exception : at ordinary/info/insert ");
        }
        return restTemplate.postForObject(SERVER_NAME+"insert",map,JSONObject.class);

    }
}
