package cn.farm.controller;

import cn.farm.entity.Spectral;
import cn.farm.exception.DataException;
import cn.farm.exception.FileException;
import cn.farm.service.SpectralService;
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
import java.util.Date;

@Slf4j
@Api(value = "farm/sysuser/spectral", tags = "光谱信息管理模块")  //描述信息用的，标注在类上
@RestController
@RequestMapping("farm/sysuser/spectral")
public class SpectralController {
    @Autowired
    SpectralService spectralService;

    @ApiOperation(value = "光谱信息添加功能")
    @PostMapping(value = "/add", produces = "application/json")
    public CommonResult SpectralAdd(Spectral spectral,
                                    @PathVariable(value = "file", required = false) MultipartFile file) {

        try {
            // 实现文件上传
            DownLoadFiles downLoadFiles = new DownLoadFiles();
            String filepath = downLoadFiles.DownLoadPhoto(file);
            spectral.setSpectralfile(filepath);

            Date date = new Date(System.currentTimeMillis());
            spectral.setTime(date);
            // 4.将spectral表的信息插入进去
            spectralService.add(spectral.getName(),spectral.getFormat(),spectral.getDescription(),spectral.getAuthor(),date,spectral.getLocation(),spectral.getDevice(),filepath);
            log.info("光谱信息添加，光谱id为："+ spectral.getSpectralid());
        } catch (DataException da) {
            log.error("资讯信息添加异常,id为："+ spectral.getSpectralid());
            throw new DataException(da.getCode(), da.getMessage());
        }catch (FileException fe){
            throw new FileException(fe.getCode(),fe.getMessage());
        }catch (IOException e) {
            e.getMessage();
        }
        return CommonResult.success(ResultCode.SUCCESS);
    }

    //光谱信息删除
    @ApiOperation(value = "光谱信息删除功能")
    @PostMapping(value = "/delete", produces = "application/json")
    public CommonResult SpectralDelete(@RequestParam("spectralid") Integer spectralid) {
        try {
            spectralService.delete(spectralid);
            log.info("删除id为："+spectralid+"的光谱信息");
            return CommonResult.success(ResultCode.SUCCESS);
        } catch (DataException d) {
            log.error("不存在id为："+spectralid+"的光谱信息，删除失败");
            throw new DataException(d.getCode(), d.getMessage());
        }
    }

    //光谱信息查询功能
    @ApiOperation(value = "光谱信息查询功能")
    @PostMapping(produces = "application/json")
    public CommonResult<PageInfo<Spectral>> SpectralFind(int page, int pagesize) {
        try {
            PageInfo<Spectral> spectrals = spectralService.selectAll(page,pagesize);
            log.info("查询所有的光谱信息");
            return CommonResult.success(spectrals);
        } catch (DataException nd) {
            log.error("光谱信息表中没有数据");
            throw new DataException(nd.getCode(), nd.getMessage());
        }
    }

    //光谱信息修改
    @ApiOperation(value = "光谱信息修改功能")
    @PostMapping(value = "/modify", produces = "application/json")
    public CommonResult SpectralModify(Spectral spectral) {
        try {
            Integer modify = spectralService.modify(spectral);
            log.info("光谱信息更新，更新id为："+ spectral.getSpectralid());
            return CommonResult.success(modify);
        } catch (DataException u) {
            log.error("更新id = "+ spectral.getSpectralid()+"的光谱信息失败");
            throw new DataException(u.getCode(), u.getMessage());
        }
    }
}
