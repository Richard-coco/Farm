package cn.farm.login.Controller;

import cn.farm.login.dto.*;
import cn.farm.login.enums.*;
import cn.farm.login.exception.DefaultException;
import cn.farm.login.exception.NoPhotoException;
import cn.farm.login.exception.NotFoundException;
import cn.farm.login.pojo.TpRegion2;
import cn.farm.login.pojo.User;
import cn.farm.login.service.AddressService;
import cn.farm.login.service.OrdinaryService;
import cn.farm.login.service.RegistService;
import cn.farm.login.service.UserModifyService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@EnableAutoConfiguration
@RequestMapping("/farm/common")
public class CommonController {
    @Autowired
    private OrdinaryService ordinaryService;
    @Autowired
    private RegistService registService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private UserModifyService userModifyService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    @RequestMapping("/loginout")
    public Result login(
            @RequestParam(value = "phone") String phone
    ){
        Result result;//返回信息
        LoginOutExecution execution;//返回Data
        User user = (User)request.getSession().getAttribute("user");
        try {
            if(user == null){//如果session中为空，那么报一个session为空的错误信息
                throw new DefaultException("注销失败");
            }
            String sessionPhone = user.getPhone();
            if(!sessionPhone.equals(phone)){
                throw new DefaultException("注销失败");
            }
            request.getSession().invalidate();
            execution = new LoginOutExecution(LoginOutStateEnum.SUCCESS);
            result = new Result(execution.getState(), execution.getStateInfo());
            return result;
        } catch (DefaultException e1) {
            execution = new LoginOutExecution(LoginOutStateEnum.DEFAULT);
            result = new Result(execution.getState(), execution.getStateInfo());
            return result;
        }
    }
//consumer写
    //获取当前session中用户的信息
    @RequestMapping("/get/user")
    public Result<SessionExecution> getUser(){
        Result<SessionExecution> result;//返回信息
        SessionExecution execution;//返回Data
        User user = null;

        try {
            user = (User)request.getSession().getAttribute("user");
            if(user == null){
                throw new DefaultException("session已过期或未登录");
            }
            execution = new SessionExecution(SessionStateEnum.SUCCESS);
            result = new Result(execution.getState(),user,execution.getStateInfo());
            return result;
        }catch (DefaultException d){
            execution = new SessionExecution(SessionStateEnum.DEFAULT);
            result = new Result(execution.getState(),user,execution.getStateInfo());
            return result;
        }catch (Exception e){
            execution = new SessionExecution(SessionStateEnum.INNER_ERROR);
            result = new Result(execution.getState(),user,execution.getStateInfo());
            return result;
        }
    }

    @RequestMapping("/login")
    public Result<loginExecution> login(
            @RequestParam(value = "phone") String phone,
            @RequestParam(value = "password") String password
    ) {
        Result<loginExecution> result;//返回信息
        loginExecution execution;//返回Data
        User user = null;
        HttpSession session = request.getSession();       // 获取Session对象

        try {
            user = ordinaryService.select(phone, password);
            session.setAttribute("user", user);// 设置Session中的属性
            session.setMaxInactiveInterval(60*60*24*7);//设置失效时间为7天
            execution = new loginExecution(loginStateEnum.SUCCESS);
            result = new Result(execution.getState(), user, execution.getStateInfo());
            return result;
        } catch (DefaultException e1) {
            execution = new loginExecution(loginStateEnum.DEFAULT);
            result = new Result(execution.getState(), execution.getStateInfo());
            return result;
        }
    }

    //普通用户注册
    @RequestMapping("ordinary/regist")
    public Result<RegistExecution> regist(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "password") String password,
            @RequestParam(value = "nickname") String nickname,
            @RequestParam(value = "phone") String phone,
            @RequestParam(value = "email") String email,
            @RequestParam(value = "address") Integer address,
            @RequestParam(value = "postcode") String postcode,
            @RequestParam(value = "introduction") String introduction,
            @RequestParam(value = "state") Integer state,
            @RequestParam(value = "role") Integer role
    )throws Exception {
        Result<RegistExecution> result;//返回信息
        RegistExecution execution;//返回Data
        User user = null;
        try {
            registService.regist(name, password, nickname, phone, email, address, postcode, introduction, state, role);
            execution = new RegistExecution(RegistStateEnum.SUCCESS);
            result = new Result(execution.getState(), execution.getStateInfo());
            return result;
        } catch (DefaultException e1) {
            execution = new RegistExecution(RegistStateEnum.DEFAULT);
            result = new Result(execution.getState(), execution.getStateInfo());
            return result;
        } catch (Exception e) {
            execution = new RegistExecution(RegistStateEnum.INNER_ERROR);
            result = new Result(execution.getState(), execution.getStateInfo());
            return result;
        }
    }

    //专家用户注册
    @RequestMapping("expert/regist")
    public Result<RegistExecution> ExprtRegist(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "password") String password,
            @RequestParam(value = "nickname") String nickname,
            @RequestParam(value = "phone") String phone,
            @RequestParam(value = "email") String email,
            @RequestParam(value = "address") Integer address,
            @RequestParam(value = "postcode") String postcode,
            @RequestParam(value = "introduction") String introduction,
            @RequestParam(value = "state") Integer state,
            @RequestParam(value = "role") Integer role
    )throws Exception {
        Result<RegistExecution> result;//返回信息
        RegistExecution execution;//返回Data
        User user = null;
        try {
            registService.ExpertRegist(name, password, nickname, phone, email, address, postcode, introduction, state, role);
            execution = new RegistExecution(RegistStateEnum.EXPERT_SUCCESS);
            result = new Result(execution.getState(), user, execution.getStateInfo());
            return result;
        } catch (DefaultException e1) {
            execution = new RegistExecution(RegistStateEnum.DEFAULT);
            result = new Result(execution.getState(), execution.getStateInfo());
            return result;
        } catch (Exception e) {
            execution = new RegistExecution(RegistStateEnum.INNER_ERROR);
            result = new Result(execution.getState(), execution.getStateInfo());
            return result;
        }
    }

    //省市县三级联动
    @RequestMapping("/linkage")
    public Result<List<TpRegion2>> linkage(
            @RequestParam(value = "level") Integer level,
            @RequestParam(value = "parentId") Integer parentId
    ) throws Exception{
        Result<List<TpRegion2>> result;//返回信息
        AddressExecution execution;//返回Data
        List<TpRegion2> list;
        list = addressService.found(level, parentId);
        execution = new AddressExecution(AddressStateEnum.SUCCESS);
        result = new Result(execution.getState(), list, execution.getStateInfo());
        return result;
    }

    //个人信息修改
    @RequestMapping("/user/modify")
    public Result userModify(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "nickname") String nickname,
            @RequestParam(value = "email") String email,
            @RequestParam(value = "address") Integer address,
            @RequestParam(value = "postcode") String postcode,
            @RequestParam(value = "introduction") String introduction
    ) throws Exception{
        Result result;//返回信息
        UserModifyExecution execution;//返回Data
        try {
            User user1 = (User) request.getSession().getAttribute("user");
            User user = userModifyService.modify(user1, name, nickname, email, address, postcode, introduction);
            request.getSession().setAttribute("user", user);
            execution = new UserModifyExecution(UserModifyEnum.SUCCESS);
            result = new Result(execution.getState(), execution.getStateInfo());
            return result;
        } catch (Exception e) {
            execution = new UserModifyExecution(UserModifyEnum.INNER_ERROR);
            result = new Result(execution.getState(), e.getMessage());
            return result;
        }

    }

    //修改密码(先确定已经使用)
    @RequestMapping(value = "/user/password")
    public Result password(
            @RequestParam(value = "password") String password,
            @RequestParam(value = "userid") Integer userid
    ) throws Exception{
        Result result;//返回信息
        PasswordExecution execution;//返回Data
        try {
            User user = (User) request.getSession().getAttribute("user");
            if(user.getUserid()!=userid){
                throw new NotFoundException("账号未登录");
            }
            userModifyService.modifyPassword(user.getUserid(), password);//不需要返回值
            request.getSession().setAttribute("user",user);
            execution = new PasswordExecution(PasswordStateEnum.SUCCESS);
            result = new Result(execution.getState(), execution.getStateInfo());
            return result;
        }catch (NotFoundException n){
            execution = new PasswordExecution(PasswordStateEnum.DEFAULT);
            result = new Result(execution.getState(), execution.getStateInfo());
            return result;
        }catch (Exception e) {
            execution = new PasswordExecution(PasswordStateEnum.INNER_ERROR);
            result = new Result(execution.getState(), execution.getStateInfo());
            return result;
        }
    }

    //修改绑定电话
    @RequestMapping(value = "/user/phone")
    public Result phone(
            @RequestParam(value = "phone") String phone,
            @RequestParam(value = "userid") Integer userid
    ) throws Exception{
        Result result;//返回信息
        PhoneExecution execution;//返回Data
        try {
            User user = (User)request.getSession().getAttribute("user");
            if(user.getUserid()!=userid){
                throw new NotFoundException("账号未登录");
            }
            userModifyService.modifyPhone(user.getUserid(), phone);//不需要返回值
            user.setPhone(phone);
            request.getSession().setAttribute("user",user);
            execution = new PhoneExecution(PhoneStateEnum.SUCCESS);
            result = new Result(execution.getState(), execution.getStateInfo());
            return result;
        } catch (DefaultException n){
            execution = new PhoneExecution(PhoneStateEnum.DEFAULT);
            result = new Result(execution.getState(), execution.getStateInfo());
            return result;
        }catch (Exception e) {
            execution = new PhoneExecution(PhoneStateEnum.INNER_ERROR);
            result = new Result(execution.getState(), execution.getStateInfo());
            return result;
        }
    }

    //修改用户头像
    @RequestMapping(value = "/user/modifyphoto")
    public Result ModifyUserPhoto(
            @RequestParam(value = "photo") MultipartFile photo//pageNum,pageSize
    ) throws IOException,Exception {
        Result result;
        PhotoExecution execution;
        User user = (User) request.getSession().getAttribute("user");
        try {
            if (photo != null) {
                //获取上传文件的原始名称
                String originalFilename = photo.getOriginalFilename();
                String pic_path;
                String newFileName = "";

                // 上传图片
                if (originalFilename != null && originalFilename.length() > 0) {
                    //获取Tomcat服务器所在的路径
                    String tomcat_path = System.getProperty("user.dir");
                    System.out.println(tomcat_path);
                    //获取Tomcat服务器所在路径的最后一个文件目录
                    String bin_path = tomcat_path.substring(tomcat_path.lastIndexOf("\\") + 1, tomcat_path.length());
                    System.out.println(bin_path);
                    //若最后一个文件目录为bin目录，则服务器为手动启动
                    if (("bin").equals(bin_path)) {//手动启动Tomcat时获取路径为：
                        //获取保存上传图片的文件路径
                        pic_path = tomcat_path.substring(0, System.getProperty("user.dir").lastIndexOf("\\")) + "\\img\\" ;
                    } else {//服务中自启动Tomcat时获取路径为：
                        pic_path = tomcat_path + "\\img\\";
                    }
                    // 新的图片名称
                    newFileName = UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf("."));
                    System.out.println("上传图片的路径：" + pic_path + newFileName);
                    // 新图片
                    File newFile = new File(pic_path + newFileName);
                    // 将内存中的数据写入磁盘
                    photo.transferTo(newFile);

                    //给定绝对路径
                    String photoPath = "\\img\\"+newFileName;

                    userModifyService.ModifyPhoto(user.getUserid(),photoPath);
                    user.setPhoto(photoPath);
                }
            } else {
                //上传图片为空，报图片空异常
                throw new NoPhotoException("图片为空");
            }
            execution = new PhotoExecution(PhotoStateEnum.SUCCESS);
            result = new Result(execution.getState(),execution.getStateInfo());
            request.getSession().setAttribute("user",user);
            return result;
        }catch (DefaultException e){
            execution = new PhotoExecution(PhotoStateEnum.DEFAULT);
            result = new Result(execution.getState(),execution.getStateInfo());
            return result;
        }catch (Exception e){
            execution = new PhotoExecution(PhotoStateEnum.INNER_ERROR);
            result = new Result(execution.getState(),e.getMessage());
            return result;
        }
    }
}
