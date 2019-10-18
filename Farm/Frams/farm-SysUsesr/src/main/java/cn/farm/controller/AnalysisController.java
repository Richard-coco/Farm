package cn.farm.controller;

import cn.farm.entity.Analysis;
import cn.farm.exception.*;
import cn.farm.service.AnalysisService;
import cn.farm.utils.CommonResult;
import cn.farm.utils.ResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;


@Log4j2
@Api(value = "farm/sysuser/analysis", tags = "情报分析管理模块")  //描述信息用的，标注在类上
@RestController
@RequestMapping("farm/sysuser/analysis")
public class AnalysisController {
    @Autowired
    AnalysisService analysisService;

    //情报分析增加
    @ApiOperation(value = "情报分析添加功能")
    @PostMapping(value = "/add", produces = "application/json")
    public CommonResult AnalysisAdd(Analysis analysis) {
        try {
            Date date = new Date(System.currentTimeMillis());
            analysis.setTime(date);
            analysisService.add(analysis.getInfoid(), analysis.getAnalysis(), analysis.getExpert(), date);
           log.info("情报分析数据添加，id为"+analysis.getInfoid());
            return CommonResult.success(ResultCode.SUCCESS);
        } catch (DataException dae) {
            log.error("添加id = "+analysis.getInfoid()+"的情报分析数据失败");
            throw new DataException(dae.getCode(), dae.getMessage());
        }
    }

    //情报分析删除
    @ApiOperation(value = "情报分析删除功能")
    @PostMapping(value = "/delete", produces = "application/json")
    public CommonResult AnalysisDelete(@RequestParam("infoid") Integer infoid) {
        try {
            analysisService.delete(infoid);
            log.info("执行删除id = "+infoid+"的情报分析数据");
            return CommonResult.success(ResultCode.SUCCESS);
        } catch (DataException dde) {
            log.error("id = "+infoid+"的情报分析数据删除失败");
            throw new DataException(dde.getCode(), dde.getMessage());
        }
    }

    //情报分析查询
    @ApiOperation(value = "情报分析查询功能")
    @PostMapping(produces = "application/json")
    public CommonResult<List<Analysis>> AnalysisFind() {
        try {
            List<Analysis> analyses = analysisService.selectAll();
            if (analyses != null) {
                log.info("查询所有情报分析数据");
                return CommonResult.success(analyses);
            } else {
                throw new DataException("400", "不存在情报分析数据");
            }
        } catch (DataException dfe) {
            log.error("不存在情报分析数据");
            throw new DataException(dfe.getCode(), dfe.getMessage());
        }
    }

    //情报分析修改
    @ApiOperation(value = "情报分析修改功能")
    @PostMapping(value = "/modify", produces = "application/json")
    public CommonResult AnalysisModify(Analysis analysis) {
        try {
            analysisService.modify(analysis);
            log.info("修改id = "+analysis.getInfoid()+"的情报分析数据");
        } catch (DataException due) {
            log.error("id = "+analysis.getInfoid()+"的情报分析数据更新失败");
            throw new DataException(due.getCode(), due.getMessage());
        }
        return CommonResult.success(ResultCode.SUCCESS);
    }
}
