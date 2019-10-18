package cn.farm.controller;

import cn.farm.entity.Message;
import cn.farm.exception.*;
import cn.farm.service.MessageService;
import cn.farm.utils.CommonResult;
import cn.farm.utils.ResultCode;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
@Slf4j
@Api(value = "farm/sysuser/message", tags = "资讯信息管理模块")  //描述信息用的，标注在类上
@RestController
@RequestMapping("farm/sysuser/message")
public class MessageController {
    @Autowired
    MessageService messageService;
    //植保知识增加
       @ApiOperation(value = "资讯添加功能")
       @PostMapping(value = "/add", produces = "application/json")
       public CommonResult MessageAdd(Message message){
           try{
               Date date = new Date(System.currentTimeMillis());
               message.setDate(date);
               messageService.add(message.getTitle(),message.getKeyword(),message.getAuthor(),date,message.getWebpage());
               log.info("资讯信息添加，胁迫情报id为："+message.getMessid());
               return CommonResult.success(ResultCode.SUCCESS);
           }catch (DataException da){
               log.error("资讯信息添加异常,id为："+message.getMessid());
               throw new DataException(da.getCode(),da.getMessage());
           }
       }

    //资讯删除
    @ApiOperation(value = "资讯删除功能")
    @PostMapping(value = "/delete", produces = "application/json")
    public CommonResult MessageDelete(@RequestParam(value = "messid") Integer messid) {
        try {
            messageService.delete(messid);
            log.info("删除id为："+messid+"的资讯信息");
            return CommonResult.success(ResultCode.SUCCESS);
        } catch (DataException dde) {
            log.error("不存在id为："+messid+"的资讯信息，删除失败");
            throw new DataException(dde.getCode(), dde.getMessage());
        }
    }

    //资讯查询
    @ApiOperation(value = "资讯知识查询功能")
    @PostMapping(produces = "application/json")
    public CommonResult<PageInfo<Message>> MessageFind(int page, int pagesize) {
        try {
            PageInfo<Message> messages = messageService.selectAll(page,pagesize);
            log.info("查询所有的资讯信息");
            return CommonResult.success(messages, "ok");
        } catch (DataException dfe) {
            log.error("资讯信息表中没有数据");
            throw new DataException(dfe.getCode(), dfe.getMessage());
        }
    }

    //资讯修改
    @ApiOperation(value = "资讯修改功能")
    @PostMapping(value = "/modify", produces = "application/json")
    public CommonResult MessageModify(Message message) {
        try {
            Integer modify = messageService.modify(message);
            log.info("资讯信息更新，更新id为："+ message.getMessid());
            return CommonResult.success(modify);
        } catch (DataException u) {
            log.error("更新id = "+message.getMessid()+"的资讯信息失败");
            throw new DataException(u.getCode(), u.getMessage());
        }
    }
}
