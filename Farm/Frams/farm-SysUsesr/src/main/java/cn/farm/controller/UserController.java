package cn.farm.controller;

import cn.farm.entity.Plant;
import cn.farm.entity.User;
import cn.farm.exception.DataException;
import cn.farm.service.CropService;
import cn.farm.service.ImageService;
import cn.farm.service.Match_ImageService;
import cn.farm.service.UserService;
import cn.farm.utils.CommonResult;
import cn.farm.utils.ImageUtil;
import cn.farm.utils.ResultCode;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Slf4j
@Api(value = "farm/sysuser", tags = "用户信息管理模块")  //描述信息用的，标注在类上
@RestController
@RequestMapping("farm/sysuser")
public class UserController {
    @Autowired
    ImageUtil<User> imageUtil;
    @Autowired
    ImageService imageService;
    @Autowired
    Match_ImageService matchImageService;
    @Autowired
    UserService userService;
    @Autowired
    private CropService cropService;

    //1.1 查询待审核用户
    @ApiOperation(value = "查询state=0的待审核用户", notes = "查询所有state =0 的用户")
    @PostMapping(value = "/user/expertuser/reviewing", produces = "application/json")
    public CommonResult<PageInfo<User>> findExperWaitAgree(int page,int pagesize) {
        log.info("查询第"+page+"页的待审核用户信息");
        return CommonResult.success(userService.getUserByState(0,page,pagesize));
    }

    //1.2 查询审核通过用户
    @ApiOperation(value = "查询state=1的审核通过用户", notes = "查询所有state =1 的用户")
    @PostMapping(value = "/user/expertuser", produces = "application/json")
    public CommonResult<PageInfo<User>> findExperAgree(int page,int pagesize) {
        log.info("查询第"+page+"页的审核通过用户信息");
        return CommonResult.success(userService.getUserByState(1,page,pagesize));
    }

    //1.3 更新角色
    @ApiOperation(value = "通过phone更新state", notes = "实现是否审核通过成为专家用户")
    @PostMapping(value = "/user/expertuser/agree", produces = "application/json")
    public CommonResult updateRoleByPhone(@RequestParam("phone") String phone, @RequestParam("state") Integer state) {
        CommonResult commonResult;
        int count = userService.updateUserRole(phone, state);
        //判断一个手机号是否有多个用户
        if (count == 1) {
            commonResult = CommonResult.success(state);
            log.info("升级成功，状态码为："+state);
        } else {
            log.error("未查询到电话为："+phone+"的用户信息");
            commonResult = CommonResult.failed(ResultCode.VALIDATE_FAILED);
        }
        return commonResult;
    }

    /*
     * 2.删除专家用户
     * */
    @ApiOperation(value = "通过phone级联删除指定用户", notes = "删除单个用户")
    @PostMapping(value = "/user/expertuser/delete", produces = "application/json")
    public CommonResult<String> deleteRoleByPhone(@RequestParam("phone") String phone) {
        int count = userService.deleteUserByPhone(phone);
        if (count == 1) {
            log.info("删除成功,phone = "+ phone);
            return CommonResult.success(phone);
        } else {
            log.error("不存在手机号为"+phone+"的用户，删除失败");
            return CommonResult.failed(ResultCode.VALIDATE_FAILED);
        }
    }
    /*---------------------------------------------------------------------------*/

    /*
     * 修改专家用户信息
     * */
    @ApiOperation(value = "修改专家用户信息")
    @PostMapping(value = "/user/expertuser/modify", produces = "application/json")
    public CommonResult modifyExper(User user){
        int i = userService.modifyExpertMsg(user);
        if (i != 0){
            log.info("修改id为"+user.getUserid()+"的用户信息信息成功");
            return CommonResult.success(user, "修改成功");
        }else {
            log.error("修改id为"+user.getUserid()+"的用户信息信息失败");
            return CommonResult.failed("修改失败");
        }
    }

    /*------------------------------------------------------------------------------------*/
    /*
     * 1.管理员登录
     * */
    @ApiOperation(value = "管理员登录后台功能")
    @PostMapping(value = "/login", produces = "application/json")
    public CommonResult loginSysUser(@RequestParam("phone") String phone, @RequestParam("password") String password, Map<String, Object> map, HttpSession session) {
        System.out.println("phone:"+phone+",passaord:"+password);

        if ("123456".equals(password) && "10086".equals(phone)) {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            attributes.getRequest().getSession().setAttribute("phone", phone); //将登陆用户信息存入到session域对象中
            log.info("管理员登录，phone = "+phone+",password = "+ password);
            return CommonResult.success(ResultCode.SUCCESS);
        } else {
            map.put("msg", "服务器错误");
            return CommonResult.failed(ResultCode.FAILED);
        }
    }
    /**
     * 管理员注销
     *
     * @return
     */
    @RequestMapping(value = "/logout",produces = "application/json")
    public CommonResult logout() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        attributes.getRequest().getSession().removeAttribute("phone");
        return CommonResult.success(ResultCode.SUCCESS);
    }

    /*
     * 普通用户信息管理
     * */

    // 1.根据用户电话查询用户信息
    @ApiOperation(value = "查询指定普通用户信息")
    @PostMapping(value = "/user/generaluser/phone", produces = "application/json")
    public CommonResult<User> QueryGeneralByPhone(String phone, HttpSession session) {
       User user = userService.queryGeneralByPhone(phone);
        try {
            if (user != null) {
                session.setAttribute("phone", phone);
                log.info(String.valueOf(user));
                return CommonResult.success(user);
            } else {
                log.error("不存在手机号为："+phone+"的用户，查询失败");
                return CommonResult.failed("用户不存在，查询失败");
            }
        } catch (DataException q) {
            throw new DataException(q.getCode(), q.getMessage());
        }

    }

    // 查询所有普通角色用户的信息
    @ApiOperation(value = "查询普通角色用户的信息")
    @GetMapping(value = "/user/generaluser", produces = "application/json")
    public CommonResult<PageInfo<User>> QueryGeneral(int page,int pagesize) {
        try {
            PageInfo<User> user = userService.queryGeneral(3,page,pagesize);
            log.info("第"+page+"页普通用户信息查询");
            return CommonResult.success(user);
        } catch (DataException q) {
            log.error("第"+page+"页普通用户信息查询失败");
            throw new DataException(q.getCode(), q.getMessage());
        }
    }

    @ApiOperation(value = "通过phone级联删除指定用户", notes = "删除单个用户")
       @PostMapping(value = "/user/general/delete", produces = "application/json")
       public CommonResult<Plant> deleteGeneralByPhone(@RequestParam("phone") String phone) {
           int count = userService.deleteGeneralByPhone(phone);
           if (count == 1) {
               log.info("成功删除phone为"+phone+"的用户的信息");
               return CommonResult.success(null);
           } else {
               log.error("用户phone为："+phone+"的信息删除失败");
               return CommonResult.failed("操作删除失败");
           }
       }

    @ApiOperation(value = "修改普通用户信息")
    @PostMapping(value = "/user/generaluser/modify", produces = "application/json")
    public CommonResult modifyGeneral(User user) {
        userService.modifyGeneralMsg(user);
        log.info("根据phone = "+user.getPhone()+"修改该用户信息");
        return CommonResult.success(user, "修改成功");
    }

}
