package cn.farm.controller;

import cn.farm.entity.Diagnosis;
import cn.farm.exception.DataException;
import cn.farm.service.DiagnosisService;
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

@Api(value = "farm/sysuser/diagnosis", tags = "诊断信息管理模块")  //描述信息用的，标注在类上
@Slf4j
@RestController
@RequestMapping("farm/sysuser/diagnosis")
public class DiagnosisController {
    @Autowired
    DiagnosisService diagnosisService;

    @ApiOperation(value = "诊断信息添加功能")
    @PostMapping(value = "/add", produces = "application/json")
    public CommonResult DiagnosisAdd(Diagnosis diagnosis) {
        try {
            diagnosisService.add(diagnosis.getInquiryid(),diagnosis.getRuleid(),diagnosis.getModelid(),diagnosis.getType(),diagnosis.getDegree());
             log.info("问诊编号为："+diagnosis.getInquiryid()+"数据添加成功");

        } catch (DataException dae) {
            log.error("问诊编号为："+diagnosis.getInquiryid()+"的诊断信息添加失败");
            throw new DataException(dae.getCode(), dae.getMessage());
        }
        return CommonResult.success(ResultCode.SUCCESS);
    }

    //诊断删除
    @ApiOperation(value = "诊断删除功能")
    @PostMapping(value = "/delete", produces = "application/json")
    public CommonResult DiagnosisDelete(@RequestParam("inquiryid") Integer inquiryid) {
        try {
            diagnosisService.delete(inquiryid);
            log.info("问诊编号为："+inquiryid+"的诊断信息删除成功");
            return CommonResult.success(ResultCode.SUCCESS);
        } catch (DataException da) {
            log.error("诊断信息删除失败");
            throw new DataException(da.getCode(), da.getMessage());
        }
    }

    //诊断信息查询
     @ApiOperation(value = "诊断信息分页查询功能")
     @PostMapping(produces = "application/json")
     public CommonResult<PageInfo<Diagnosis>> DiagnosisFind(int page,int pagesize) {
         try {
             PageInfo<Diagnosis> diagnosis = diagnosisService.getDiagnosisPages(page,pagesize);
             if (diagnosis != null) {
                 log.info("查询第"+page+"页的诊断信息，共有"+pagesize+"个");
                 return CommonResult.success(diagnosis);
             } else {
                 // 如果查询的数据不存在，直接返回空的数据集
                 return null;
             }
         } catch (DataException nd) {
             log.error("不存在诊断分析数据，查询失败");
             throw new DataException(nd.getCode(), nd.getMessage());
         }
     }

}
