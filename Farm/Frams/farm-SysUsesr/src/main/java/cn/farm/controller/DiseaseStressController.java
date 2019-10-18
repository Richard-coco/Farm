package cn.farm.controller;

import cn.farm.entity.Diseasestress;
import cn.farm.entity.Image;
import cn.farm.entity.Match_Image;
import cn.farm.entity.Stresscategory;
import cn.farm.exception.DataException;
import cn.farm.exception.NotImageException;
import cn.farm.service.CropService;
import cn.farm.service.DiseaseStressService;
import cn.farm.service.ImageService;
import cn.farm.service.Match_ImageService;
import cn.farm.utils.CommonResult;
import cn.farm.utils.ImageUtil;
import cn.farm.utils.ResultCode;
import cn.farm.utils.Translation.DiseaseStressTranslation;
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
@Api(value = "farm/sysuser/stresscategory/diseasestress", tags = "病号胁迫类型信息管理模块")  //描述信息用的，标注在类上
@RestController
@RequestMapping("farm/sysuser/stresscategory/diseasestress")
public class DiseaseStressController {
    @Autowired
    DiseaseStressService diseaseStressService;
    @Autowired
    Match_ImageService matchImageService;
    @Autowired
    ImageService imageService;
    @Autowired
    ImageUtil<Diseasestress> imageUtil;
    @Autowired
    CropService cropService;

    //病害胁迫增加
    @ApiOperation(value = "病害胁迫添加功能")
    @PostMapping(value = "/add", produces = "application/json")
    public CommonResult DiseaseStressAdd(
            Stresscategory stresscategory,
            Diseasestress diseasestress,
            @RequestParam("plantname") String plantname,
            // 病原图像，设置两张图片
            @PathVariable(value = "file1", required = false) MultipartFile file1,
            @PathVariable(value = "file2", required = false) MultipartFile file2,
            // 症状图像，设置两张图片
            @PathVariable(value = "file3", required = false) MultipartFile file3,
            @PathVariable(value = "file4", required = false) MultipartFile file4
    ) throws NotImageException {
        List<MultipartFile> multipartFiles1 = new ArrayList<>();
        // 1.1. 添加病原图像法
        multipartFiles1.add(file1);
        multipartFiles1.add(file2);
        // 1.2. 添加症状图像
        List<MultipartFile> multipartFiles2 = new ArrayList<>();
        multipartFiles2.add(file3);
        multipartFiles2.add(file4);

        List<Image> images1;
        List<Image> images2;

        try {
            // 1.3 将病原图像插入
            images1 = imageUtil.dealImage(multipartFiles1, diseasestress, new DiseaseStressTranslation());
            List<Integer> imageIds1 = imageService.addImageList(images1);
            List<Match_Image> match_images1 = new ArrayList<>();
            Match_Image matchImage;
            for (int imageId : imageIds1) {
                matchImage = new Match_Image(imageId, "病害胁迫的病原图像");
                match_images1.add(matchImage);
            }
            // 1.3.1.将match_images信息插入进去,返回的是pathoImage对应的字段
            int pathoImage = matchImageService.addListImage(match_images1);
            // 1.3.2 将上面返回的pathoImage字段传入diseasestress的Pathoimage字段
            diseasestress.setPathoimage(pathoImage);

            // 1.4 将病症图像插入
            images2 = imageUtil.dealImage(multipartFiles2, diseasestress, new DiseaseStressTranslation());
            List<Integer> imageIds2 = imageService.addImageList(images2);
            List<Match_Image> match_images2 = new ArrayList<>();
            for (int imageId : imageIds2) {
                matchImage = new Match_Image(imageId, "病害胁迫的病症图像");
                match_images2.add(matchImage);
            }
            // 1.4.1.将match_images信息插入进去，返回的是syptomImage对应的字段
            int symptomImage = matchImageService.addListImage(match_images2);
            // 1.4.2 将上面返回的syptomImage 字段传入diseasestress的Syptomimage字段
            diseasestress.setSymptomimage(symptomImage);

            // 1.5 将plantid传给cropid
            int plantid = cropService.findPlantId(plantname);
            diseasestress.setCropid(plantid);

            // 1.5 调用diseaseStressService.add()方法添加数据
            diseaseStressService.add(plantid, diseasestress.getChinesename(), diseasestress.getLatinname(),
                    diseasestress.getAlias(), diseasestress.getDistribution(),
                    diseasestress.getSymptom(), diseasestress.getPathogeny(),
                    diseasestress.getOccurrence(), diseasestress.getControl(),
                    pathoImage, symptomImage, stresscategory.getName(), stresscategory.getMaintype(),
                    stresscategory.getSubtype(), stresscategory.getFactor());
            log.info("植物id为："+diseasestress.getCropid()+"的病害胁迫数据添加成功,");
            return CommonResult.success(ResultCode.SUCCESS.getState(), "ok");
        } catch (IOException e) {
            e.getMessage();
        } catch (NotImageException e) {
            throw new NotImageException(e.getCode(),e.getMessage());
        } catch (DataException dae) {
            log.error("cropid = "+diseasestress.getCropid()+"的病害信息添加失败");
            throw new DataException(dae.getCode(), dae.getMessage());
        }
        return CommonResult.success(ResultCode.SUCCESS);
    }


    //病害胁迫删除
    @ApiOperation(value = "病害胁迫删除功能")
    @PostMapping(value = "/delete", produces = "application/json")
    public CommonResult DiseaseStressDelete (@RequestParam("diseaseid") Integer diseaseid){
        try {
            diseaseStressService.delete(diseaseid);
            log.info("成功删除diseaseid为："+diseaseid+"的数据");
            return CommonResult.success("删除成功");
        } catch (DataException dde) {
            log.error("删除diseaseid为："+diseaseid+"的数据失败");
            throw new DataException(dde.getCode(), dde.getMessage());
        }
    }

    //病害胁迫查询
    @ApiOperation(value = "病害胁迫查询功能")
    @PostMapping(produces = "application/json")
    public CommonResult<PageInfo<Diseasestress>> DiseaseStressFind(int page,int pagesize) {
        try {
            PageInfo<Diseasestress> list = diseaseStressService.selectAll(page,pagesize);
            if (list != null){
                log.info("查询第"+page+"页的病害");
                return CommonResult.success(list);
            }else {
                return null;
            }
        } catch (DataException dfe) {
            log.error("病害胁迫表中没有数据");
            throw new DataException(dfe.getCode(), dfe.getMessage());
        }
    }

    //病害胁迫修改
    @ApiOperation(value = "病害胁迫修改功能")
    @PostMapping(value = "/modify", produces = "application/json")
    public CommonResult DiseaseStressModify(Diseasestress diseasestress) {
        try {
            Integer modify = diseaseStressService.modify(diseasestress);
            log.info("病害胁迫信息更新，更新id为："+diseasestress.getDiseaseid());
            return CommonResult.success( modify,"ok");
        } catch (DataException due) {
            log.error("更新id = "+diseasestress.getDiseaseid()+"的病害胁迫信息更新失败");
            throw new DataException(due.getCode(), due.getMessage());
        }
    }
}
