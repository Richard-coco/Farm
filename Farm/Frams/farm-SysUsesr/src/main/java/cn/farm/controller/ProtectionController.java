package cn.farm.controller;

import cn.farm.entity.Protection;
import cn.farm.exception.*;
import cn.farm.service.ProtectionService;
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
@Slf4j
@Api(value = "farm/sysuser/protection", tags = "植保知识信息管理模块")  //描述信息用的，标注在类上
@RestController
@RequestMapping("farm/sysuser/protection")
public class ProtectionController {
    @Autowired
    ProtectionService protectionService;

    //植保知识增加
    @ApiOperation(value = "植保知识添加功能")
    @PostMapping(value = "/add", produces = "application/json")
    public CommonResult ProtectionAdd(Protection protection){
        try{
            Date date = new Date(System.currentTimeMillis());
            protection.setDate(date);
            protectionService.add(protection.getTitle(),protection.getKeyword(),protection.getAuthor(),date,protection.getWebpage());
            log.info("植保知识添加，植保知识id为："+ protection.getKnowid());
            return CommonResult.success(ResultCode.SUCCESS);
        }catch (DataException da){
            log.error("植保知添加异常,id为："+ protection.getKnowid());
            throw new DataException(da.getCode(),da.getMessage());
        }
    }

    //植保知识删除
    @ApiOperation(value = "植保知识删除功能")
    @PostMapping(value = "/delete", produces = "application/json")
    public CommonResult ProtectionDelete(@RequestParam(value = "knowid") Integer knowid) {
        try {
            protectionService.delete(knowid);
            log.info("删除id为："+knowid+"的植保知识信息");
            return CommonResult.success(ResultCode.SUCCESS);
        }catch (DataException d){
            log.error("不存在id为："+knowid+"的植保知识信息，删除失败");
            throw new DataException(d.getCode(),d.getMessage());
        }
    }

    //植保知识查询
        @ApiOperation(value = "植保知识查询功能")
        @PostMapping(produces = "application/json")
        public CommonResult<PageInfo<Protection>> ProtectionFind(int page,int pagesize) {
            try {
                PageInfo<Protection> protections = protectionService.selectAll(page,pagesize);
                log.info("查询所有的植保知识信息");
                return CommonResult.success(protections);
            } catch (DataException nd) {
                log.error("植保知识表中没有数据");
                throw new DataException(nd.getCode(), nd.getMessage());
            }
        }

    //植保知识修改
    @ApiOperation(value = "植保知识修改功能")
    @PostMapping(value = "/modify",produces = "application/json")
    public CommonResult ProtectionModify(Protection protection) {
        try {
            Integer modify = protectionService.modify(protection);
            log.info("植保知识更新，更新id为："+ protection.getKnowid());
            return CommonResult.success(modify);
        } catch (DataException u) {
            log.error("更新id = "+ protection.getKnowid()+"的植保知识失败");
            throw new DataException(u.getCode(), u.getMessage());
        }
    }
}
