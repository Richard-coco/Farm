package cn.farm.controller;

import cn.farm.entity.Image;
import cn.farm.entity.Match_Image;
import cn.farm.entity.Plant;
import cn.farm.exception.*;
import cn.farm.service.CropService;
import cn.farm.service.ImageService;
import cn.farm.service.Match_ImageService;
import cn.farm.utils.CommonResult;
import cn.farm.utils.ImageUtil;
import cn.farm.utils.Translation.PlantTranslation;
import cn.farm.utils.ResultCode;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;


@Log4j2
@Api(value = "farm/sysuser", tags = "作物信息管理模块")  //描述信息用的，标注在类上
@RestController
@RequestMapping("farm/sysuser/crop")
public class CropController {

    @Autowired
    private CropService cropService;
    @Autowired
    ImageUtil<Plant> imageUtil;
    @Autowired
    ImageService imageService;
    @Autowired
    Match_ImageService matchImageService;

    //作物增加
    @ApiOperation(value = "作物信息添加功能")
    @PostMapping(value = "/add", produces = "application/json")
    public CommonResult CorpAdd(
            @RequestParam(value = "kingdom", required = false) String kingdom,
            @RequestParam(value = "phylum", required = false) String phylum,
            @RequestParam(value = "bio_class", required = false) String bio_class,
            @RequestParam(value = "order", required = false) String order,
            @RequestParam(value = "family", required = false) String family,
            @RequestParam(value = "genus", required = false) String genus,
            @RequestParam(value = "species", required = false) String species,//界门纲目科属种

            @RequestParam(value = "plantimage", required = false) Integer plantimage,//植物图像
            @RequestParam(value = "chinesename", required = false) String chinesename,
            @RequestParam(value = "latinname", required = false) String latinname,
            @RequestParam(value = "alias", required = false) String alias,
            @RequestParam(value = "morphology", required = false) String morphology,
            @RequestParam(value = "growthhabit", required = false) String growthhabit,
            @RequestParam(value = "distribution", required = false) String distribution,
            @PathVariable(value = "file1", required = false) MultipartFile file1,
            @PathVariable(value = "file2", required = false) MultipartFile file2,
            Plant plant) throws NotImageException {
        List<MultipartFile> multipartFiles = new ArrayList<>();
        multipartFiles.add(file1);
        multipartFiles.add(file2);
        List<Image> images;
        try {
            images = imageUtil.dealImage(multipartFiles, plant, new PlantTranslation());
            List<Integer> imageIds = imageService.addImageList(images);
            List<Match_Image> match_images = new ArrayList<>();
            Match_Image matchImage;
            for (int imageId : imageIds) {
                matchImage = new Match_Image(imageId, "植物表的植物图像");
                match_images.add(matchImage);
            }
            int matchid = matchImageService.addListImage(match_images);
            plant.setPlantimage(matchid);
            cropService.add(matchid,kingdom, phylum, bio_class, order, family, genus, species, chinesename, latinname, alias, distribution, morphology, growthhabit);
            log.info("作物信息添加，作物id为："+plant.getPlantid());

        } catch (DataException dae) {
            log.error("添加作物信息表时，生物分类添加失败");
            throw new DataException(dae.getCode(), dae.getMessage());
        } catch (ImageAddException iae) {
            log.error("添加作物信息表时，图片插入失败");
            throw new ImageAddException(iae.getCode(), iae.getMessage());
        } catch (NotImageException e) {
            throw new NotImageException(e.getCode(),e.getMessage());
        } catch (IOException e) {
            e.getMessage();
        }

        return CommonResult.success(ResultCode.SUCCESS);
    }

    @ApiOperation(value = "作物信息删除功能", notes = "删除单条数据")
    @PostMapping(value = "/delete", produces = "application/json")
    public CommonResult CropDelete(@RequestParam("plantid") Integer plantid) {
        try {
            cropService.deletePlant(plantid);
           log.info("删除id为："+plantid+"的作物信息");
            return CommonResult.success("200", "ok");
        } catch (DataException dde) {
            //controller层抛出的异常，将返回给前端进行报错提示
            log.error("不存在id为："+plantid+"的作物信息，删除失败");
            throw new DataException(dde.getCode(), dde.getMessage());
        }
    }


    @ApiOperation(value = "分页查询作物信息", notes = "分页查询作物")
        @PostMapping(produces = "application/json")
        public CommonResult<PageInfo<Plant>> listAllPlant(int page,int pagesize) {
            try {
                PageInfo<Plant> plants = cropService.getPlantPages(page,pagesize);
                if (plants != null) {
                    log.info("查询第"+page+"页的植物信息，共有"+pagesize+"个");
                    return CommonResult.success(plants);
                } else {
                    log.error("植物信息表中没有数据");
                    return CommonResult.failed(ResultCode.FAILED);
                }
            } catch (DataException dfe) {
                throw new DataException(dfe.getCode(), dfe.getMessage());
            }
        }

    @ApiOperation(value = "查询作物信息", notes = "查询单个作物")
    @PostMapping(value = "/{plantid}", produces = "application/json")
    public CommonResult<Plant> findPlantById(@PathVariable(name = "plantid") Integer plantid, Plant plant) {
        try {
            Plant singlPlant = cropService.findSinglePlant(plantid);
            log.info("查询植物id为:"+plantid+"的数据");
            return CommonResult.success(singlPlant, "查询成功");
        } catch (DataException dfe) {
            throw new DataException(dfe.getCode(), dfe.getMessage());
        }
    }


    @ApiOperation(value = "修改作物信息")
    @PostMapping(value = "/modify", produces = "application/json")
    public CommonResult updatePlant(
            @RequestParam(value = "plantid") Integer plantid,
            @RequestParam(value = "chinesename") String chinesename,
            @RequestParam(value = "latinname") String latinname,
            @RequestParam(value = "category") Integer category,
            @RequestParam(value = "alias") String alias,
            @RequestParam(value = "morphology") String morphology,
            @RequestParam(value = "growthhabit") String growthhabit,
            @RequestParam(value = "distribution") String distribution,
            @RequestParam(value = "plantimage") Integer plantimage
    ) {
        try {
            cropService.updatePlant(plantid, chinesename, latinname, category, alias,
                    morphology, growthhabit, distribution, plantimage);
            log.info("更新作物信息数据，更新的id为："+plantid);
            return CommonResult.success(ResultCode.SUCCESS);
        } catch (DataException due) {
            log.error("更新作物的id为："+plantid+"的信息失败");
            throw new DataException(due.getCode(), due.getMessage());
        }

    }


}
