package cn.farm.Controller.SysUserController;

import cn.farm.Entity.SysUser.User;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("farm/sysuser/user")
public class UserController {

    @Autowired
    RestTemplate restTemplate;

    final String SERVER_NAME = "http://farm-SysUser/farm/sysuser/user/";
    /*
    用户信息管理
     */

    /*
    专家模块
     */

    //删除专家用户
    @PostMapping(value = "/expertuser/delete", produces = "application/problem+json")
    public JSONObject deleteRoleByPhone(@RequestParam("phone") String phone){
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add("phone",phone);
        return restTemplate.postForObject(SERVER_NAME+"expertuser/delete",param,JSONObject.class);
    }

    //修改专家用户
    @PostMapping(value = "/expertuser/modify", produces = "application/problem+json;charset=UTF-8")
    public JSONObject modifyExper(User user){
        MultiValueMap<String, Object> param = userToMultiValueMap(user);
        return restTemplate.postForObject(SERVER_NAME+"expertuser/modify",param,JSONObject.class);
    }

    //查询待审核用户
    @PostMapping(value = "expertuser/reviewing", produces = "application/problem+json;charset=UTF-8")
    public JSONObject findExperWaitAgree(int page,int pagesize){
        MultiValueMap<String,Object> param = new LinkedMultiValueMap<>();
        param.add("page",page);
        param.add("pagesize",pagesize);
        return restTemplate.postForObject(SERVER_NAME+"expertuser/reviewing",param,JSONObject.class);
    }

    //查询审核通过用户
    @PostMapping(value = "expertuser", produces = "application/problem+json;charset=UTF-8")
    public JSONObject findExperAgree(int page,int pagesize){
        MultiValueMap<String,Object> param = new LinkedMultiValueMap<>();
        param.add("page",page);
        param.add("pagesize",pagesize);
        return restTemplate.postForObject(SERVER_NAME+"expertuser",page,JSONObject.class);
    }

    //更新角色
    @PostMapping(value = "expertuser/agree", produces = "application/problem+json;charset=UTF-8")
    public JSONObject updateRoleByPhone(@RequestParam("phone") String phone, @RequestParam("state") Integer state){
        MultiValueMap<String,Object> param = new LinkedMultiValueMap<>();
        param.add("phone",phone);
        param.add("state",state);
        return restTemplate.postForObject(SERVER_NAME+"expertuser/agree",param,JSONObject.class);
    }


    /*
    普通用户信息管理
     */

    //.根据用户电话查询用户信息
    @PostMapping(value = "phone", produces = "application/problem+json;charset=UTF-8")
    public JSONObject QueryGeneralByPhone(HttpSession session ,String phone){
        MultiValueMap<String,Object> param = new LinkedMultiValueMap<>();
        param.add("phone",phone);
        session.setAttribute("phone", phone);
        return restTemplate.postForObject(SERVER_NAME+"phone",param,JSONObject.class);
    }

    // 查询所有普通角色用户的信息
    @GetMapping(value = "generaluser", produces = "application/problem+json;charset=UTF-8")
    public JSONObject QueryGeneral(int page,int pagesize){
        Map<String,Object> map = new HashMap<>();
        map.put("page",page);
        map.put("pagesize",pagesize);
        return restTemplate.getForObject(SERVER_NAME+"generaluser?page={page}&pagesize={pagesize}",JSONObject.class,map);
    }

    //通过phone级联删除指定用户
    @PostMapping(value = "general/delete", produces = "application/problem+json;charset=UTF-8")
    public JSONObject deleteGeneralByPhone(@RequestParam("phone") String phone){
        MultiValueMap<String,Object> param = new LinkedMultiValueMap<>();
        param.add("phone",phone);
        return restTemplate.postForObject(SERVER_NAME+"general/delete",param,JSONObject.class);
    }

    //修改普通用户信息
    @PostMapping(value = "generaluser/modify", produces = "application/problem+json")
    public JSONObject modifyGeneral(User user){
        MultiValueMap<String, Object> param = userToMultiValueMap(user);
        return restTemplate.postForObject(SERVER_NAME+"generaluser/modify",param,JSONObject.class);
    }


    //user转化到 MultiValueMap
    public MultiValueMap<String,Object> userToMultiValueMap(User user){
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add("userid",user.getUserid());
        param.add("password",user.getPassword());
        param.add("role",user.getRole());
        param.add("name",user.getName());
        param.add("nickname",user.getNickname());
        param.add("phone",user.getPhone());
        param.add("email",user.getEmail());
        param.add("address",user.getAddress());
        param.add("postcode",user.getPostcode());
        param.add("photo",user.getPhoto());
        param.add("introduction",user.getIntroduction());
        param.add("state",user.getState());
        return param;
    }
}
