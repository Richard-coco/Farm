package cn.farm.controller;

import cn.farm.entity.Stressfeature;
import cn.farm.exception.DataException;
import cn.farm.exception.FileException;
import cn.farm.service.StressFeatureService;
import cn.farm.utils.CommonResult;
import cn.farm.utils.DownLoadFiles;
import cn.farm.utils.ResultCode;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Api(value = "farm/sysuser/stressfeature", tags = "胁迫特征管理模块")  //描述信息用的，标注在类上
@RestController
@RequestMapping("farm/sysuser/stressfeature")
public class StressFeatureController {
    @Autowired
    StressFeatureService stressFeatureService;
    @ApiOperation(value = "胁迫特征添加功能")
    @PostMapping(value = "/add", produces = "application/json")
    public CommonResult StressFeatureAdd(Stressfeature stressfeature,
                                         @PathVariable(value = "file1", required = false) MultipartFile file1,
                                         @PathVariable(value = "file2", required = false) MultipartFile file2) {

        try {
            // 实现文件上传
            DownLoadFiles downLoadFiles = new DownLoadFiles();
            String paramfile = downLoadFiles.DownLoadPhoto(file1);
            String vectfile = downLoadFiles.DownLoadPhoto(file2);
            stressfeature.setParamfile(paramfile);
            stressfeature.setVectfile(vectfile);

            stressFeatureService.add(stressfeature.getStresstype(),paramfile,vectfile);
            log.info("胁迫特征信息添加，胁迫特征id为："+ stressfeature.getStessid());
        } catch (DataException da) {
            log.error("胁迫特征信息添加异常,id为："+stressfeature.getStessid());
            throw new DataException(da.getCode(), da.getMessage());
        } catch (FileException fe) {
            throw new FileException(fe.getCode(), fe.getMessage());
        } catch (IOException e) {
            e.getMessage();
        }
        return CommonResult.success(ResultCode.SUCCESS);
    }

    //胁迫特征删除
    @ApiOperation(value = "胁迫特征删除功能")
    @PostMapping(value = "/delete", produces = "application/json")
    public CommonResult StressFeatureDelete(@RequestParam("stessid") Integer stessid) {
        try {
            stressFeatureService.delete(stessid);
            log.info("删除id为："+stessid+"的胁迫特征信息");
        } catch (DataException d) {
            log.error("不存在id为："+stessid+"的胁迫特征信息，删除失败");
            throw new DataException(d.getCode(), d.getMessage());
        }
        return CommonResult.success(ResultCode.SUCCESS);
    }

    //胁迫特征查询功能
    @ApiOperation(value = "胁迫特征查询功能")
    @PostMapping(produces = "application/json")
    public CommonResult<PageInfo<Stressfeature>> StressFeatureFind(int page, int pagesize) {
        try {
            PageInfo<Stressfeature> Stressfeatures = stressFeatureService.selectAll(page,pagesize);
            log.info("查询所有的胁迫特征信息");
            return CommonResult.success(Stressfeatures, "ok");
        } catch (DataException nd) {
            log.error("胁迫特征信息表中没有数据");
            throw new DataException(nd.getCode(), nd.getMessage());
        }
    }

    //胁迫特征修改
    @ApiOperation(value = "胁迫特征修改功能")
    @PostMapping(value = "/modify", produces = "application/json")
    public CommonResult StressFeatureModify(Stressfeature stressfeature ,
                                             @PathVariable(value = "file1", required = false) MultipartFile file1,
                                             @PathVariable(value = "file2", required = false) MultipartFile file2) {

        try {
            // 实现文件上传
            DownLoadFiles downLoadFiles = new DownLoadFiles();
            String paramfile = downLoadFiles.DownLoadPhoto(file1);
            String vectfile = downLoadFiles.DownLoadPhoto(file2);
            stressfeature.setParamfile(paramfile);
            stressfeature.setVectfile(vectfile);
             stressFeatureService.modify(stressfeature);
            log.info("胁迫特征信息更新，更新id为："+ stressfeature.getStessid());
           // return CommonResult.success(modify);
        } catch (DataException u) {
            log.error("更新id = "+ stressfeature.getStessid()+"的胁迫特征信息失败");
            throw new DataException(u.getCode(), u.getMessage());
        } catch (IOException e) {
            e.getMessage();
        }
        return CommonResult.success(ResultCode.SUCCESS);
    }

}
