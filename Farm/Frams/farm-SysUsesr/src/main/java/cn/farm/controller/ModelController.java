package cn.farm.controller;

import cn.farm.entity.Model;
import cn.farm.exception.*;
import cn.farm.service.ModelService;
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
import java.util.List;
@Slf4j
@Api(value = "farm/sysuser/model", tags = "诊断模型管理模块")  //描述信息用的，标注在类上
@RestController
@RequestMapping("farm/sysuser/model")
public class ModelController {
    @Autowired
    ModelService modelService;

    @ApiOperation(value ="诊断模型添加功能")
    @PostMapping(value = "/add", produces = "application/json")
    public CommonResult ModelAdd(Model model,
                                @PathVariable(value = "file", required = false) MultipartFile file) {

        try {
            // 实现文件上传
            DownLoadFiles downLoadFiles = new DownLoadFiles();
            String filepath = downLoadFiles.DownLoadPhoto(file);
            model.setModelfile(filepath);

            // 4.将model表的信息插入进去
            modelService.add(model.getModelname(),model.getAuthor(),filepath,model.getDescription());
            log.info("诊断模型添加，诊断模型id为："+model.getModelid());
            return CommonResult.success(ResultCode.SUCCESS);
        }catch (FileException fe){
            throw new FileException(fe.getCode(),fe.getMessage());
        } catch (IOException e) {
            e.getMessage();
        }
        return  CommonResult.success(ResultCode.SUCCESS);
    }

    @ApiOperation(value = "诊断模型删除功能")
    @PostMapping(value = "/delete", produces = "application/json")
    public CommonResult ModelDelete(@RequestParam("modelid") Integer modelid) {
        try {
            modelService.delete(modelid);
            log.info("删除id为："+modelid+"的资讯信息");
            return CommonResult.success(ResultCode.SUCCESS);
        } catch (DataException dde) {
            log.error("不存在id为："+modelid+"的资讯信息，删除失败");
            throw new DataException(dde.getCode(), dde.getMessage());
        }
    }

    //诊断模型查询功能
      @ApiOperation(value = "诊断模型查询功能")
      @PostMapping(produces = "application/json")
      public CommonResult<PageInfo<Model>> ModelFind(int page, int pagesize) {
          try {
              PageInfo<Model> rules = modelService.selectAll(page,pagesize);
              log.info("查询所有的诊断模型信息");
              return CommonResult.success(rules);
          } catch (DataException nd) {
              log.error("诊断模型表中没有数据");
              throw new DataException(nd.getCode(), nd.getMessage());
          }
      }

    //诊断模型修改
    @ApiOperation(value = "诊断模型修改功能")
    @PostMapping(value = "/modify", produces = "application/json")
    public CommonResult ModelModify(Model model) {
        try {
            Integer modify = modelService.modify(model);
            log.info("诊断模型信息更新，更新id为："+ model.getModelid());
            return CommonResult.success(modify);
        } catch (DataException u) {
            log.error("更新id = "+ model.getModelid()+"的诊断模型信息失败");
            throw new DataException(u.getCode(), u.getMessage());
        }
    }
}
