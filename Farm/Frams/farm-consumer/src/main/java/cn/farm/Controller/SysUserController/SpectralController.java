package cn.farm.Controller.SysUserController;

import cn.farm.Entity.SysUser.Spectral;
import cn.farm.Util.FileUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("farm/sysuser/spectral")
public class SpectralController {

    @Autowired
    RestTemplate restTemplate;

    final String SERVER_NAME = "http://farm-SysUser/farm/sysuser/spectral/";


    //光谱信息添加功能
    @PostMapping(value = "/add", produces = "application/json")
    public JSONObject SpectralAdd(Spectral spectral,
                                  @PathVariable(value = "file", required = false) MultipartFile file){
        MultiValueMap<String,Object> param = new LinkedMultiValueMap<>();
        param.add("spectral",spectral);
        FileUtil.addFile(file,null,null,param);
        return restTemplate.postForObject(SERVER_NAME+"add",param,JSONObject.class);
    }

    //光谱信息删除
    @PostMapping(value = "/delete", produces = "application/json")
    public JSONObject SpectralDelete(@RequestParam("spectralid") Integer spectralid){
        MultiValueMap<String,Object> param = new LinkedMultiValueMap<>();
        param.add("spectralid",spectralid);
        return restTemplate.postForObject(SERVER_NAME+"delete",param,JSONObject.class);
    }

    //光谱信息查询功能
    @PostMapping(produces = "application/json")
    public JSONObject SpectralFind(int page, int pagesize){
        MultiValueMap<String,Object> param = new LinkedMultiValueMap<>();
        param.add("page",page);
        param.add("pagesize",pagesize);
        return restTemplate.postForObject(SERVER_NAME,param,JSONObject.class);
    }

    //光谱信息修改
    @PostMapping(value = "/modify", produces = "application/json")
    public JSONObject SpectralModify(Spectral spectral){
        MultiValueMap<String,Object> param = new LinkedMultiValueMap<>();
        param.add("spectral",spectral);
        return restTemplate.postForObject(SERVER_NAME+"modify",param,JSONObject.class);
    }
}
