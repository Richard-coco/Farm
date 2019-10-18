package cn.farm.controller;

import cn.farm.entity.*;
import cn.farm.exception.*;
import cn.farm.service.ImageService;
import cn.farm.service.InformationService;
import cn.farm.service.Match_ImageService;
import cn.farm.service.VideoService;
import cn.farm.utils.CommonResult;
import cn.farm.utils.ImageUtil;
import cn.farm.utils.ResultCode;
import cn.farm.utils.Translation.InformationTranslation;
import cn.farm.utils.Translation.VideoTranslate.InformationVideoTranslation;
import cn.farm.utils.VideoUtil;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
@Slf4j
@Api(value = "farm/sysuser/information", tags = "胁迫情报管理模块")  //描述信息用的，标注在类上
@RestController
@RequestMapping("farm/sysuser/information")
public class InformationController {
    @Autowired
    InformationService informationService;
    @Autowired
    ImageUtil<Information> imageUtil;
    @Autowired
    ImageService imageService;
    @Autowired
    Match_ImageService matchImageService;
    @Autowired
    VideoUtil<Information> videoUtil;
    @Autowired
    VideoService videoService;

    //胁迫情报增加
    @ApiOperation(value = "胁迫情报添加功能")
    @PostMapping(value = "/add", produces = "application/json")
    public CommonResult InformationAdd(Information information,
                                       // 情报图像，设置两张图片
                                       @PathVariable(value = "image1", required = false) MultipartFile image1,
                                       @PathVariable(value = "image2", required = false) MultipartFile image2,
                                       // 情报视频
                                       @PathVariable(value = "video", required = false) MultipartFile video
    ) throws NotImageException {
        List<MultipartFile> multipartFiles1 = new ArrayList<>();
        // 1.1. 添加情报图像法
        multipartFiles1.add(image1);
        multipartFiles1.add(image2);
        List<Image> images1;
        Video video1;

        try {
            // 1.3 将情报图像插入
            images1 = imageUtil.dealImage(multipartFiles1, information, new InformationTranslation());
            List<Integer> imageIds1 = imageService.addImageList(images1);
            List<Match_Image> match_images1 = new ArrayList<>();
            Match_Image matchImage;
            for (int imageId : imageIds1) {
                matchImage = new Match_Image(imageId, "情报图像");
                match_images1.add(matchImage);
            }

            int images = matchImageService.addListImage(match_images1);

            information.setImages(images);

            // 视频上传
           video1 = videoUtil.dealVideo(video,information,new InformationVideoTranslation());

            Integer videoid = videoService.addVideo(video1);

            information.setVideos(videoid);

            // 设置时间为当前时间
            Date date = new Date(System.currentTimeMillis());
            information.setTime(date);
            informationService.add(information.getTitle(), information.getContent(), information.getAuthor(), date, information.getLocation(), images, videoid);
            log.info("胁迫情报信息添加，胁迫情报id为："+information.getInfoid());
            return CommonResult.success(ResultCode.SUCCESS);
        } catch (DataException da) {
            log.error("胁迫情报添加异常,id为："+information.getInfoid());
            throw new DataException(da.getCode(), da.getMessage());
        } catch (NotImageException e) {
            log.error("添加胁迫情报表时，插入的不是图像");
            throw new NotImageException(e.getCode(),e.getMessage());
        } catch (IOException e) {
            e.getMessage();
        }
        return CommonResult.success(ResultCode.SUCCESS);
    }

    //胁迫情报删除
    @ApiOperation(value = "胁迫情报删除功能")
    @PostMapping(value = "/delete", produces = "application/json")
    public CommonResult InformationDelete(@RequestParam("infoid") Integer infoid) {
        try {
            informationService.delete(infoid);
            log.info("删除id为："+infoid+"的胁迫情报信息");
            return CommonResult.success(ResultCode.SUCCESS);
        } catch (DataException dde) {
            log.error("不存在id为："+infoid+"的胁迫情报，删除失败");
            throw new DataException(dde.getCode(), dde.getMessage());
        }
    }

    //胁迫情报查询
    @ApiOperation(value = "胁迫情报查询功能")
    @PostMapping(produces = "application/json")
    public CommonResult<PageInfo<Information>> InformationFind(int page,int pagesize) {
        try {
            PageInfo<Information> list = informationService.selectAll(page,pagesize);
            if (list != null) {
                log.info("查询第"+page+"页胁迫情报信息");
                return CommonResult.success(list);
            } else {
                return null;
            }

        } catch (DataException dfe) {
            log.error("胁迫情报表中没有数据");
            throw new  DataException(dfe.getCode(), dfe.getMessage());
        }
    }

    //胁迫情报修改
    @ApiOperation(value = "胁迫情报修改功能")
    @PostMapping(value = "/modify", produces = "application/json")
    public CommonResult InformationModify(Information information) {
        try {
            Integer modify = informationService.modify(information);
            log.info("胁迫情报更新，更新id为："+information.getInfoid());
            return CommonResult.success(modify, "ok");
        } catch (DataException due) {
            log.error("更新id = "+information.getInfoid()+"的胁迫情报失败");
            throw new DataException(due.getCode(), due.getMessage());
        }
    }
}
