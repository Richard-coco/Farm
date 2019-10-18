package cn.farm.controller;

import cn.farm.entity.*;
import cn.farm.exception.*;
import cn.farm.service.EnemyService;
import cn.farm.service.ImageService;
import cn.farm.service.Match_ImageService;
import cn.farm.utils.*;
import cn.farm.utils.Translation.EnenmyTranslation;
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
@Api(value = "farm/sysuser", tags = "天敌信息管理模块")  //描述信息用的，标注在类上
@RestController
@RequestMapping("farm/sysuser/enemy")
public class EnemyController {
    @Autowired
    ImageUtil<Enemy> imageUtil;
    @Autowired
    ImageService imageService;
    @Autowired
    Match_ImageService matchImageService;
    @Autowired
    EnemyService enemyService;


    @ApiOperation(value = "天敌信息添加功能")
    @PostMapping(value = "/add", produces = "application/json")
    public CommonResult EnemyAdd(Biologycategory biologycategory,
                                 Enemy enemy,
                                 @PathVariable(value = "file1", required = false) MultipartFile file1,
                                 @PathVariable(value = "file2", required = false) MultipartFile file2) throws NotImageException {
        List<MultipartFile> multipartFiles = new ArrayList<>();
        multipartFiles.add(file1);
        multipartFiles.add(file2);
        List<Image> images;
        // 1.将images信息插入进去
        try {
            // 1.1 先调用imageUtil.dealImage方法，处理插入的图片
            images = imageUtil.dealImage(multipartFiles, enemy, new EnenmyTranslation());
            // 1.2 将图片封装成list集合
            List<Integer> imageIds = imageService.addImageList(images);
            // 1.3 将Match_image封装成list集合
            List<Match_Image> match_images = new ArrayList<>();
            Match_Image matchImage;
            // 1.4 遍历imagesIds集合
            for (int imageId : imageIds) {
                // 1.5 每次遍历将imageId传入Match_Image以及matchinfo传入固定参数
                matchImage = new Match_Image(imageId, "天敌(enemy)信息表图像");
                // 1.6 将得到的matchImage添加到match_images集合中
                match_images.add(matchImage);
            }
            // 2.将match_images信息插入进去
            int matchid = matchImageService.addListImage(match_images);
            // 3.将得到的matchid插入enemy表的imageid中，但此时还未插入到数据库中
            enemy.setImageid(matchid);
            // 4.将enemy表的信息插入进去
            enemyService.add(biologycategory.getKingdom(), biologycategory.getPhylum(), biologycategory.getBio_class(),
                    biologycategory.getOrder(), biologycategory.getFamily(), biologycategory.getGenus(),
                    biologycategory.getSpecies(), enemy.getChinesename(), enemy.getLatinname(),
                    enemy.getAlias(), enemy.getDistribution(), enemy.getMorphology(), matchid, enemy.getLifehabit());
            log.info("天敌信息添加，天敌id为："+enemy.getEnemyid());
            return CommonResult.success(ResultCode.SUCCESS);
        } catch (IOException e) {
            e.getMessage();
        } catch (NotImageException e) {
            throw new NotImageException(e.getCode(),e.getMessage());
        }
        return CommonResult.success(ResultCode.SUCCESS);
    }

    @ApiOperation(value = "天敌信息删除功能")
    @PostMapping(value = "/delete", produces = "application/json")
    public CommonResult EnemyDelete(@RequestParam("enemyid") Integer enemyid) {
        try {
             enemyService.delete(enemyid);
            log.info("删除id为："+enemyid+"的天敌信息");
            return CommonResult.success(ResultCode.SUCCESS);
        } catch (DataException dde) {
            log.error("不存在id为："+enemyid+"的天敌信息，删除失败");
            throw new DataException(dde.getCode(), dde.getMessage());
        }
    }

    @ApiOperation(value = "天敌信息查询功能")
    @PostMapping(produces = "application/json")
    public CommonResult<PageInfo<Enemy>> EnemysFind(int page,int pagesize) {
        try {
            PageInfo<Enemy> enemies = enemyService.findList(page,pagesize);
            if (enemies != null) {
                log.info("查询第"+page+"页天敌信息");
               return CommonResult.success(enemies);
            } else {
                return null;
            }
        } catch (DataException dfe) {
            log.error("天敌信息表中没有数据");
            throw new DataException(dfe.getCode(), dfe.getMessage());
        }
    }

    @ApiOperation(value = "天敌信息更新功能")
      @PostMapping(value = "/modify",produces = "application/json")
      public CommonResult EnemyUpdate(Enemy enemy) {
        try{
            enemyService.update(enemy);
            log.info("天敌信息更新，更新id为："+enemy.getEnemyid());
            return CommonResult.success(ResultCode.SUCCESS);
        }catch (DataException due){
            log.error("更新id = "+enemy.getEnemyid()+"的天敌信息失败");
            throw new DataException(due.getCode(),due.getMessage());
        }
      }
}
