package cn.farm.controller;

import cn.farm.entity.*;
import cn.farm.exception.*;
import cn.farm.service.*;
import cn.farm.utils.CommonResult;
import cn.farm.utils.ImageUtil;
import cn.farm.utils.ResultCode;
import cn.farm.utils.Translation.AbioticStressTranslation;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Api(value = "farm/sysuser/stresscategory/abioticstress", tags = "非生物胁迫类型信息管理模块")  //描述信息用的，标注在类上
@RestController
@RequestMapping("farm/sysuser/stresscategory/abioticstress")
public class AbioticStressController {
    @Autowired
    AbioticStressService abioticStressService;
    @Autowired
    Match_ImageService matchImageService;
    @Autowired
    ImageService imageService;
    @Autowired
    ImageUtil<Abioticstress> imageUtil;
    @Autowired
    CropService cropService;
    @Autowired
    BiologyCategoryService biologyCategoryService;

    //非生物胁迫增加
    @ApiOperation(value = "非生物胁迫添加功能")
    @PostMapping(value = "/add", produces = "application/json")
    public CommonResult AbioticStressAdd(
            Stresscategory stresscategory,
            Abioticstress abioticstress,
            @RequestParam("plantname") String plantname,
            // 症状图像，设置两张图片
            @PathVariable(value = "file1", required = false) MultipartFile file1,
            @PathVariable(value = "file2", required = false) MultipartFile file2
    ) {
        // 1.2. 添加症状图像
        List<MultipartFile> multipartFiles = new ArrayList<>();
        multipartFiles.add(file1);
        multipartFiles.add(file2);

        List<Image> images;

        try {
            // 1.4 将症状图像插入
            images = imageUtil.dealImage(multipartFiles, abioticstress, new AbioticStressTranslation());
            List<Integer> imageIds = imageService.addImageList(images);
            List<Match_Image> match_images = new ArrayList<>();
            Match_Image matchImage;
            for (int imageId : imageIds) {
                matchImage = new Match_Image(imageId, "非生物胁迫的症状图像");
                match_images.add(matchImage);
            }
            // 1.4.1.将match_images信息插入进去，返回的是syptomImage对应的字段
            int symptomImage = matchImageService.addListImage(match_images);
            // 1.4.2 将上面返回的symptomImage 字段传入weedstress的Symptomimage字段
            abioticstress.setSymptomimage(symptomImage);
            // 1.5 将plantid传给cropid
            int plantid = cropService.findPlantId(plantname);
            abioticstress.setCropid(plantid);
            // 1.5 调用weedStressService.add()方法添加数据
            abioticStressService.add(plantid, stresscategory.getName(), stresscategory.getMaintype(),
                    stresscategory.getSubtype(), stresscategory.getFactor(), abioticstress.getHarm(),
                    abioticstress.getSymptom(), abioticstress.getControl(), symptomImage);
            log.info("调用weedStressService.add()方法添加数据");

        } catch (IOException e) {
            log.error("写入症状图像文件发生异常",e);
        } catch (NotImageException e) {
            log.error("写入的不是图片文件",e);
        } catch (DataException dae) {
            log.error("非生物胁迫数据添加失败",dae);
            throw new DataException(dae.getCode(), dae.getMessage());
        }
        return CommonResult.success(ResultCode.SUCCESS);
    }

    //非生物胁迫删除
    @ApiOperation(value = "非生物胁迫删除功能")
    @PostMapping(value = "/delete", produces = "application/json")
    public CommonResult AbioticStressDelete(@RequestParam("abioticid") Integer abioticid) {
        try {
            abioticStressService.delete(abioticid);
            log.info("执行非生物胁迫删除");
            return CommonResult.success(ResultCode.SUCCESS);
        } catch (DataException dde) {
            log.error("非生物胁迫删除异常");
            throw new DataException(dde.getCode(), dde.getMessage());
        }
    }

    //非生物胁迫查询
    @ApiOperation(value = "非生物胁迫查询功能")
    @PostMapping(produces = "application/json")
    public CommonResult<PageInfo<Abioticstress>> AbioticStressFind(int page,int pagesize) {
        try {
            PageInfo<Abioticstress> list = abioticStressService.selectAll(page,pagesize);
            if (list != null) {
                log.info("非生物胁迫查询成功");
                return CommonResult.success(list);
            } else {
                throw new DataException("400", "不存在非生物胁迫数据");
            }

        } catch (DataException dfe) {
            throw new DataException(dfe.getCode(), dfe.getMessage());
        }
    }

    //非生物胁迫修改
    @ApiOperation(value = "非生物胁迫修改功能")
    @PostMapping(value = "/modify", produces = "application/json")
    public CommonResult AbiotictressModify(Abioticstress abioticstress) {
        try {
            abioticStressService.modify(abioticstress);
            log.info("非生物胁迫的cropid为："+abioticstress.getCropid()+"的信息修改成功");
            return CommonResult.success(ResultCode.SUCCESS);
        } catch (DataException due) {
            log.error("cropid为："+abioticstress.getCropid()+"的非生物胁迫信息修改失败");
            throw new DataException(due.getCode(), due.getMessage());
        }
    }
}
