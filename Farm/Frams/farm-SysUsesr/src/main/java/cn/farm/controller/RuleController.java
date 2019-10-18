package cn.farm.controller;

import cn.farm.entity.Rule;
import cn.farm.exception.DataException;
import cn.farm.exception.FileException;
import cn.farm.service.CropService;
import cn.farm.service.RuleService;
import cn.farm.service.StressCategoryService;
import cn.farm.utils.CommonResult;
import cn.farm.utils.DownLoadFiles;
import cn.farm.utils.ResultCode;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Log4j2
@Api(value = "farm/sysuser/rule", tags = "诊断规则管理模块")  //描述信息用的，标注在类上
@RestController
@RequestMapping("farm/sysuser/rule")
public class RuleController {

    @Autowired
    RuleService ruleService;
    @Autowired
    CropService cropService;
    @Autowired
    StressCategoryService stressCategoryService;


    @ApiOperation(value = "诊断规则添加功能")
    @PostMapping(value = "/add", produces = "application/json")
    public CommonResult RuleAdd(Rule rule,
                                @RequestParam("stressname") String stressname,
                                @RequestParam("plantname") String plantname,
                                @PathVariable(value = "file", required = false) MultipartFile file) {

        try {
            // 实现文件上传
            DownLoadFiles downLoadFiles = new DownLoadFiles();
            String filepath = downLoadFiles.DownLoadPhoto(file);
            rule.setRulefile(filepath);
            // System.out.println(filepath);
            // 1.5 将plantid传给cropid
            int plantid = cropService.findPlantId(plantname);
            rule.setCropid(plantid);
            // 将stresstype传给rule.getStressType();
            Integer stressId = stressCategoryService.findStressId(stressname);
            rule.setStresstype(stressId);
            // 4.将question表的信息插入进去
            ruleService.add(rule.getRulename(), rule.getAuthor(), stressId, plantid, filepath, rule.getDescription());
            log.info("诊断规则信息添加，诊断规则id为："+rule.getRuleid());
            return CommonResult.success(ResultCode.SUCCESS);
        } catch (FileException fe) {
            throw new FileException(fe.getCode(), fe.getMessage());
        } catch (IOException e) {
            e.getMessage();
        }
        return CommonResult.success(ResultCode.SUCCESS);
    }

    @ApiOperation(value = "诊断规则删除功能")
    @PostMapping(value = "/delete", produces = "application/json")
    public CommonResult RuleDelete(@RequestParam("ruleid") Integer ruleid) {
        try {
            ruleService.delete(ruleid);
            log.info("删除id为："+ruleid+"的诊断规则信息");
            return CommonResult.success(ResultCode.SUCCESS);
        } catch (DataException d) {
            log.error("不存在id为："+ruleid+"的诊断规则信息，删除失败");
            throw new DataException(d.getCode(), d.getMessage());
        }
    }

    //诊断规则查询功能
    @ApiOperation(value = "诊断规则查询功能")
    @PostMapping(produces = "application/json")
    public CommonResult<PageInfo<Rule>> RuleFind(int page,int pagesize) {
        try {
            PageInfo<Rule> rules = ruleService.selectAll(page,pagesize);
            log.info("查询所有的诊断规则信息");
            return CommonResult.success(rules, "ok");
        } catch (DataException nd) {
            log.error("诊断规则信息表中没有数据");
            throw new DataException(nd.getCode(), nd.getMessage());
        }
    }

    //诊断规则修改
        @ApiOperation(value = "诊断规则修改功能")
        @PostMapping(value = "/modify", produces = "application/json")
        public CommonResult RuleModify(Rule rule) {
            try {
                Integer modify = ruleService.modify(rule);
                log.info("诊断规则信息更新，更新id为："+ rule.getRuleid());
                return CommonResult.success(modify);
            } catch (DataException u) {
                log.error("更新id = "+  rule.getRuleid()+"的诊断规则信息失败");
                throw new DataException(u.getCode(), u.getMessage());
            }
        }
}
