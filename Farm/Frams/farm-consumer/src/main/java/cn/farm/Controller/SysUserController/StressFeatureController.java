package cn.farm.Controller.SysUserController;


import cn.farm.Entity.SysUser.Stressfeature;
import cn.farm.Util.FileUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("farm/sysuser/stressfeature")
public class StressFeatureController {

    @Autowired
    RestTemplate restTemplate;

    final String SERVER_NAME= "http://farm-SysUser/farm/sysuser/stressfeature";

    //胁迫特征添加功能
    @PostMapping(value = "/add", produces = "application/problem+json")
    public JSONObject StressFeatureAdd(Stressfeature stressfeature,
                                       @PathVariable(value = "file1", required = false) MultipartFile file1,
                                       @PathVariable(value = "file2", required = false) MultipartFile file2){

        MultiValueMap<String,Object> param = new LinkedMultiValueMap<>();
        param.add("stresstype",stressfeature.getStresstype());
        param.add("paramfile",stressfeature.getParamfile());
        param.add("vectfile",stressfeature.getVectfile());
        FileUtil.addFile(file1,file2,null,param);
        return restTemplate.postForObject(SERVER_NAME+"add",param,JSONObject.class);
    }

    //胁迫特征删除
    @PostMapping(value = "/delete", produces = "application/problem+json")
    public JSONObject StressFeatureDelete(@RequestParam("stessid") Integer stessid){
        MultiValueMap<String,Object> param = new LinkedMultiValueMap<>();
        param.add("stessid",stessid);
        return restTemplate.postForObject(SERVER_NAME+"delete",param,JSONObject.class);
    }

    //胁迫特征查询功能
    @PostMapping(produces = "application/problem+json")
    public JSONObject StressFeatureFind(int page, int pagesize){
        MultiValueMap<String,Object> param = new LinkedMultiValueMap<>();
        param.add("page",page);
        param.add("pagesize",pagesize);
        return restTemplate.postForObject(SERVER_NAME,param,JSONObject.class);
    }

    //胁迫特征修改
    @PostMapping(value = "/modify", produces = "application/problem+json")
    public JSONObject StressFeatureModify(Stressfeature stressfeature){
        MultiValueMap<String,Object> param = new LinkedMultiValueMap<>();
        param.add("stresstype",stressfeature);
        param.add("paramfile",stressfeature);
        param.add("vectfile",stressfeature);
        return restTemplate.postForObject(SERVER_NAME+"modify",param,JSONObject.class);
    }
}
