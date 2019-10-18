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

@RequestMapping("farm/expert/model")
@RestController
public class ModelController {

    final String SERVER_NAME = "http://farm-Expert/farm/expert/model/";

    @Autowired
    RestTemplate restTemplate;

    /**
     * 查询录入的诊断模型
     * @param pageSize
     * @param pageNum
     * @return
     */
    @IsLogin
    @GetMapping(value = "find",produces = "application/json")
    public JSONObject findByAuthor(HttpSession session,
                                   @RequestParam(value = "pagesize",defaultValue = "3") int  pageSize,
                                   @RequestParam(value = "pagenum",defaultValue = "1")int pageNum){
        Map<String,Object> map = new HashMap<>();
        map.put("userid", UserUtil.getUserID(session));
        map.put("pagesize",pageSize);
        map.put("pagenum",pageNum);
        return restTemplate.getForObject(SERVER_NAME+"find?userid{userid}&pagesize={pagesize}&pagenum={pagenum}",JSONObject.class,map);
    }

    /**
     * 添加诊断模型
     * @param file
     * @param modelName
     * @param description
     * @return
     */
    @IsLogin
    @PostMapping(value = "save",produces = "application/json")
    public JSONObject add(HttpSession session,
                    @RequestParam(value = "modelfile",required = true) MultipartFile file,
                    @RequestParam(value = "modelname",required = false)String modelName,
                    @RequestParam(value = "description",required = false)String description){
        MultiValueMap<String,Object> param = new LinkedMultiValueMap<>();
        param.add("userid",UserUtil.getUserID(session));
        if(file != null){
            try {
                param.add("modelfile", FileUtil.multipartFileToFileSystemResource(file));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        param.add("modelname",modelName);
        param.add("description",description);
        return restTemplate.postForObject(SERVER_NAME+"add",param,JSONObject.class);
    }

    /**
     * 修改诊断模型 不能更新model文件
     * @param file
     * @param modelId
     * @param modelName
     * @param description
     * @return
     */
    @IsLogin
    @PostMapping(value = "update",produces = "application/json")
    public JSONObject update(HttpSession session,
                       @RequestParam(value = "modelfile",required = false) MultipartFile file,
                       @RequestParam(value = "modelid",required = false)int modelId,
                       @RequestParam(value = "modelname",required = false)String modelName,
                       @RequestParam(value = "description",required = false)String description){
        MultiValueMap<String,Object> param = new LinkedMultiValueMap<>();
        param.add("userid",UserUtil.getUserID(session));
        if(file != null){
            try {
                param.add("modelfile",FileUtil.multipartFileToFileSystemResource(file));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        param.add("modelid",modelId);
        param.add("modelname",modelName);
        param.add("description",description);
        return restTemplate.postForObject(SERVER_NAME+"update",param,JSONObject.class);
    }
    /**
     * 删除诊断模型
     * @param
     * @return
     */
    @IsLogin
    @GetMapping(value = "remove",produces = "application/json")
    public JSONObject remove(HttpSession session,
                       @RequestParam(value = "modelid",required = true) int modelId) {
        Map<String,Object> map = new HashMap<>();
        map.put("userid",UserUtil.getUserID(session));
        map.put("modelid",modelId);
        return restTemplate.getForObject(SERVER_NAME+"remove?userid={userid}&modelid={modelid}", JSONObject.class,map);
    }

}
