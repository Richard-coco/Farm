package cn.farm.Controller.SysUserController;

import cn.farm.Entity.SysUser.Stresscategory;
import cn.farm.Entity.SysUser.Weedstress;
import cn.farm.Util.FileUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("farm/sysuser/stresscategory/weedstress")
public class WeedStressController {

    @Autowired
    RestTemplate restTemplate;

    final String SERVER_NAME = "htttp://farm-SysUser/farm/sysuser/stresscategory/weedstress/";

    //草害胁迫增加
    @PostMapping(value = "/add", produces = "application/problem+json")
    public JSONObject WeedStressAdd(  Stresscategory stresscategory,
                                      Weedstress weedstress,
                                      @RequestParam("plantname") String plantname,
                                      // 症状图像，设置两张图片
                                      @PathVariable(value = "file1", required = false) MultipartFile file1,
                                      @PathVariable(value = "file2", required = false) MultipartFile file2){

        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add("name",stresscategory.getName());
        param.add("maintype",stresscategory.getMaintype());
        param.add("subtype",stresscategory.getSubtype());
        param.add("factor",stresscategory.getFactor());

        param.add("category",weedstress.getCategory());
        param.add("harm",weedstress.getHarm());
        param.add("symptom",weedstress.getSymptom());
        param.add("control",weedstress.getControl());

        param.add("plantname",plantname);

        try {
            if(file1 != null ){
                param.add("file1",FileUtil.multipartFileToFileSystemResource(file1));
            }
            if(file2 != null ){
                param.add("file2",FileUtil.multipartFileToFileSystemResource(file2));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return restTemplate.postForObject(SERVER_NAME+"add",param,JSONObject.class);

    }

    //草害胁迫删除
    @PostMapping(value = "/delete", produces = "application/problem+json")
    public JSONObject WeedStressDelete(@RequestParam("weedid") Integer weedid){
        MultiValueMap<String,Object> param = new LinkedMultiValueMap<>();
        param.add("wedid",weedid);
        return restTemplate.postForObject(SERVER_NAME+"delete",param,JSONObject.class);
    }

    //草害胁迫查询
    @PostMapping(produces = "application/problem+json")
    public JSONObject WeedStressFind(int page,int pagesize){
        MultiValueMap<String,Object> param = new LinkedMultiValueMap<>();
        param.add("page",page);
        param.add("pagesize",pagesize);
        return restTemplate.postForObject(SERVER_NAME,param,JSONObject.class);

    }

    //草害胁迫修改
    @PostMapping(value = "/modify", produces = "application/problem+json")
    public JSONObject WeedStressModify(Weedstress weedstress){
        MultiValueMap<String,Object> param = new LinkedMultiValueMap<>();
        param.add("weedid",weedstress.getWeedid());
        param.add("stresstype",weedstress.getStresstype());
        param.add("cropid",weedstress.getCropid());
        param.add("category",weedstress.getCategory());
        param.add("harm",weedstress.getHarm());
        param.add("symptom",weedstress.getSymptom());
        param.add("control",weedstress.getControl());
        param.add("symptomImage",weedstress.getSymptomimage());
        return restTemplate.postForObject(SERVER_NAME+"modify",param, JSONObject.class);
    }
}
