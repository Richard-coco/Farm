package cn.farm.controller;

import cn.farm.entity.Image;
import cn.farm.entity.Match_Image;
import cn.farm.entity.Stresscategory;
import cn.farm.entity.Weedstress;
import cn.farm.exception.DataException;
import cn.farm.exception.NotImageException;
import cn.farm.service.*;
import cn.farm.utils.CommonResult;
import cn.farm.utils.ImageUtil;
import cn.farm.utils.ResultCode;
import cn.farm.utils.Translation.WeedStressTranslation;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Api(value = "farm/sysuser/stresscategory/weedstress", tags = "草害胁迫类型信息管理模块")  //描述信息用的，标注在类上
@RestController
@RequestMapping("farm/sysuser/stresscategory/weedstress")
public class WeedStressController {
    @Autowired
    WeedStressService weedStressService;
    @Autowired
    Match_ImageService matchImageService;
    @Autowired
    ImageService imageService;
    @Autowired
    ImageUtil<Weedstress> imageUtil;
    @Autowired
    CropService cropService;
    @Autowired
    BiologyCategoryService biologyCategoryService;

    //草害胁迫增加
    @ApiOperation(value = "草害胁迫添加功能")
    @PostMapping(value = "/add", produces = "application/json")
    public CommonResult WeedStressAdd(
            Stresscategory stresscategory,
            Weedstress weedstress,
            @RequestParam("plantname") String plantname,
            // 症状图像，设置两张图片
            @PathVariable(value = "file1", required = false) MultipartFile file1,
            @PathVariable(value = "file2", required = false) MultipartFile file2
    ) throws NotImageException {
        // 1.2. 添加症状图像
        List<MultipartFile> multipartFiles = new ArrayList<>();
        multipartFiles.add(file1);
        multipartFiles.add(file2);

        List<Image> images;

        try {
            // 1.4 将症状图像插入
            images = imageUtil.dealImage(multipartFiles, weedstress, new WeedStressTranslation());
            List<Integer> imageIds = imageService.addImageList(images);
            List<Match_Image> match_images = new ArrayList<>();
            Match_Image matchImage;
            for (int imageId : imageIds) {
                matchImage = new Match_Image(imageId, "草害胁迫的症状图像");
                match_images.add(matchImage);
            }
            // 1.4.1.将match_images信息插入进去，返回的是syptomImage对应的字段
            int symptomImage = matchImageService.addListImage(match_images);
            // 1.4.2 将上面返回的symptomImage 字段传入weedstress的Symptomimage字段
            weedstress.setSymptomimage(symptomImage);
            // 1.5 将plantid传给cropid
            int plantid = cropService.findPlantId(plantname);
            weedstress.setCropid(plantid);
            // 将bioid传给categorygory
            int bioid = biologyCategoryService.findBioId(weedstress.getCategory());
            weedstress.setCategory(bioid);
            // 1.5 调用weedStressService.add()方法添加数据
            weedStressService.add(plantid, stresscategory.getName(), stresscategory.getMaintype(),
                    stresscategory.getSubtype(), stresscategory.getFactor(), bioid, weedstress.getHarm(),
                    weedstress.getSymptom(), weedstress.getControl(), symptomImage);
            log.info("草害胁迫信息添加，草害胁迫id为："+ weedstress.getWeedid());
        } catch (DataException ws) {
            log.error("草害胁迫添加异常,id为：" + weedstress.getWeedid());
            throw new DataException(ws.getCode(), ws.getMessage());
        } catch (NotImageException e) {
            log.error("添加草害胁迫信息表时，插入的不是图像");
            throw new NotImageException(e.getCode(), e.getMessage());
        } catch (IOException e) {
            e.getMessage();
        }
        return CommonResult.success(ResultCode.SUCCESS);
    }

    //草害胁迫删除
    @ApiOperation(value = "草害胁迫删除功能")
    @PostMapping(value = "/delete", produces = "application/json")
    public CommonResult WeedStressDelete(@RequestParam("weedid") Integer weedid,Weedstress weedstress) {
        try {
           // imageService.deleteImage();
            matchImageService.deleteImage(weedstress.getSymptomimage());
            weedStressService.delete(weedid);
            log.info("删除id为："+weedid+"的草害胁迫信息");
            return CommonResult.success(ResultCode.SUCCESS);
        } catch (DataException ws) {
            log.error("不存在id为："+weedid+"的草害胁迫信息，删除失败");
            throw new DataException(ws.getCode(), ws.getMessage());
        }
    }

    //草害胁迫查询
    @ApiOperation(value = "草害胁迫查询功能")
    @PostMapping(produces = "application/json")
    public CommonResult<PageInfo<Weedstress>> WeedStressFind(int page,int pagesize) {
        try {
            PageInfo<Weedstress> list = weedStressService.selectAll(page,pagesize);
            if (list != null) {
                log.info("查询第"+page+"页的草害胁迫信息");
                return CommonResult.success(list);
            } else {
                log.error("草害胁迫信息表中没有数据");
                return null;
            }

        } catch (DataException wsf) {
            log.error("草害胁迫信息表中没有数据");
            throw new DataException(wsf.getCode(), wsf.getMessage());
        }
    }


    //草害胁迫修改
    @ApiOperation(value = "草害胁迫修改功能")
    @PostMapping(value = "/modify", produces = "application/json")
    public CommonResult WeedStressModify(Weedstress weedstress,
                                         //症状图像，设置两张图片
                @PathVariable(value = "file1", required = false) MultipartFile file1,
                @PathVariable(value = "file2", required = false) MultipartFile file2) {
        List<MultipartFile> multipartFiles = new ArrayList<>();
        // 1.1. 添加病原图像法
        multipartFiles.add(file1);
        multipartFiles.add(file2);
        List<Image> images;
        try {
            // 1.4 将病原图像插入
            images = imageUtil.dealImage(multipartFiles, weedstress, new WeedStressTranslation());
            List<Integer> imageIds = imageService.addImageList(images);
            List<Match_Image> match_images = new ArrayList<>();
            Match_Image matchImage;
            for (int imageId : imageIds) {
                matchImage = new Match_Image(imageId, "草害胁迫的病原图像");
                match_images.add(matchImage);
            }
            // 1.4.1.将match_images信息插入进去，返回的是syptomImage对应的字段
            int symptomImage = matchImageService.addListImage(match_images);
            weedstress.setSymptomimage(symptomImage);
            // 1.4.2 将上面返回的symptomImage 字段传入weedstress的Symptomimage字段
            weedStressService.modify(weedstress);
            log.info("更新草害信息数据，更新的id为："+ weedstress.getWeedid());
           // return CommonResult.success(modify);
        } catch (DataException u) {
            log.error("更新草害的id为："+ weedstress.getWeedid() +"的信息失败");
            throw new DataException(u.getCode(), u.getMessage());
        } catch (NotImageException e) {
            log.error("写入的不是图片文件",e);
        } catch (IOException e) {
            log.error("写入病原图像文件发生异常",e);
        }
        return CommonResult.success(ResultCode.SUCCESS);
    }
}
