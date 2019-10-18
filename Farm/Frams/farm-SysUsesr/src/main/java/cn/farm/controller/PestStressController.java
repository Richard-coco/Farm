package cn.farm.controller;

import cn.farm.entity.Image;
import cn.farm.entity.Match_Image;
import cn.farm.entity.Peststress;
import cn.farm.entity.Stresscategory;
import cn.farm.exception.DataException;
import cn.farm.exception.NotImageException;
import cn.farm.service.*;
import cn.farm.utils.CommonResult;
import cn.farm.utils.ImageUtil;
import cn.farm.utils.ResultCode;
import cn.farm.utils.Translation.PestStressTranslation;
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
@Api(value = "farm/sysuser/stresscategory/peststress", tags = "虫害胁迫类型信息管理模块")  //描述信息用的，标注在类上
@RestController
@RequestMapping("farm/sysuser/stresscategory/peststress")
public class PestStressController {
    @Autowired
    PestStressService pestStressService;
    @Autowired
    Match_ImageService matchImageService;
    @Autowired
    ImageService imageService;
    @Autowired
    ImageUtil<Peststress> imageUtil;
    @Autowired
    CropService cropService;
    @Autowired
    BiologyCategoryService biologyCategoryService;

    //虫害胁迫增加
    @ApiOperation(value = "虫害胁迫添加功能")
    @PostMapping(value = "/add", produces = "application/json")
    public CommonResult PestStressAdd(
            Stresscategory stresscategory,
            Peststress peststress,
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
            // 1.3 将虫害图像插入
            images1 = imageUtil.dealImage(multipartFiles1, peststress, new PestStressTranslation());
            List<Integer> imageIds1 = imageService.addImageList(images1);
            List<Match_Image> match_images1 = new ArrayList<>();
            Match_Image matchImage;
            for (int imageId : imageIds1) {
                matchImage = new Match_Image(imageId, "虫害胁迫的虫害图像");
                match_images1.add(matchImage);
            }
            // 1.3.1.将match_images信息插入进去,返回的是pathoImage对应的字段
            int pestImage = matchImageService.addListImage(match_images1);
            // 1.3.2 将上面返回的pathoImage字段传入diseasestress的Pathoimage字段
            peststress.setPestimage(pestImage);

            // 1.4 将病症图像插入
            images2 = imageUtil.dealImage(multipartFiles2, peststress, new PestStressTranslation());
            List<Integer> imageIds2 = imageService.addImageList(images2);
            List<Match_Image> match_images2 = new ArrayList<>();
            for (int imageId : imageIds2) {
                matchImage = new Match_Image(imageId, "虫害胁迫的症状图像");
                match_images2.add(matchImage);
            }
            // 1.4.1.将match_images信息插入进去，返回的是syptomImage对应的字段
            int syptomImage = matchImageService.addListImage(match_images2);
            // 1.4.2 将上面返回的syptomImage 字段传入diseasestress的Syptomimage字段
            peststress.setSyptomimage(syptomImage);

            // 1.5 将plantid传给cropid
            int plantid = cropService.findPlantId(plantname);
            peststress.setCropid(plantid);

            // 将bioid传给categorygory
            int bioid = biologyCategoryService.findBioId(peststress.getCategory());
            peststress.setCategory(bioid);

            // 1.5 调用diseaseStressService.add()方法添加数据
            pestStressService.add(plantid, peststress.getChinesename(), peststress.getLatinname(),
                    peststress.getAlias(),bioid, peststress.getDistribution(),peststress.getMorphology(),
                    peststress.getLifehabit(),peststress.getDamage(), peststress.getControl(),pestImage,
                    syptomImage, stresscategory.getName(), stresscategory.getMaintype(),
                    stresscategory.getSubtype(), stresscategory.getFactor());
            log.info("虫害胁迫信息添加，虫害胁迫id为："+peststress.getPestid());
            return CommonResult.success(ResultCode.SUCCESS);
        } catch (IOException e) {
            e.getMessage();
        } catch (NotImageException e) {
            log.error("添加虫害胁迫信息表时，插入的不是图像");
            throw new NotImageException(e.getCode(),e.getMessage());
        } catch (DataException ps) {
            log.error("虫害胁迫添加异常,id为："+ peststress.getPestid());
            throw new DataException(ps.getCode(), ps.getMessage());
        }
        return CommonResult.success(ResultCode.SUCCESS);
    }

    //虫害胁迫删除
    @ApiOperation(value = "虫害胁迫删除功能")
    @PostMapping(value = "/delete", produces = "application/json")
    public CommonResult PestStressDelete(@RequestParam("pestid") Integer pestid) {
        try {
            pestStressService.delete(pestid);
            log.info("删除id为："+pestid+"的虫害胁迫信息");
            return CommonResult.success(ResultCode.SUCCESS);
        } catch (DataException ds) {
            log.error("不存在id为："+pestid+"的虫害胁迫信息，删除失败");
            throw new DataException(ds.getCode(), ds.getMessage());
        }
    }

    //虫害胁迫查询
    @ApiOperation(value = "虫害胁迫分页查询功能")
    @PostMapping(produces = "application/json")
    public CommonResult<PageInfo<Peststress>> PestStressFind(int page,int pagesize) {
        try {
            PageInfo<Peststress> list = pestStressService.selectAll(page,pagesize);
            if (list != null) {
                log.info("查询第"+page+"页的虫害胁迫信息");
                return CommonResult.success(list);
            } else {
                log.error("虫害胁迫信息表中没有数据");
               return null;
            }
        } catch (DataException psf) {
            log.error("虫害胁迫信息表中没有数据");
            throw new DataException(psf.getCode(), psf.getMessage());
        }
    }


    //虫害胁迫修改
    @ApiOperation(value = "虫害胁迫修改功能")
    @PostMapping(value = "/modify", produces = "application/json")
    public CommonResult PestStressModify(Peststress peststress) {
        try {
            Integer modify = pestStressService.modify(peststress);
            log.info("更新虫害信息数据，更新的id为："+peststress.getPestid());
            return CommonResult.success(modify);
        } catch (DataException u) {
            log.error("更新虫害的id为："+peststress.getPestid()+"的信息失败");
            throw new DataException(u.getCode(), u.getMessage());
        }
    }

}
