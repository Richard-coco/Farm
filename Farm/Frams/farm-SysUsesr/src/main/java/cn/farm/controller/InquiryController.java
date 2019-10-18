package cn.farm.controller;

import cn.farm.entity.*;
import cn.farm.exception.*;
import cn.farm.service.*;
import cn.farm.utils.CommonResult;
import cn.farm.utils.ImageUtil;
import cn.farm.utils.ResultCode;
import cn.farm.utils.Translation.InquiryTranslation;
import cn.farm.utils.Translation.VideoTranslate.InquiryVideoTranslation;
import cn.farm.utils.VideoUtil;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Api(value = "farm/sysuser/inquiry", tags = "问诊信息管理模块")  //描述信息用的，标注在类上
@RestController
@RequestMapping("farm/sysuser/inquiry")
public class InquiryController {
    @Autowired
    InquiryService inquiryService;
    @Autowired
    ImageUtil<Inquiry> imageUtil;
    @Autowired
    ImageService imageService;
    @Autowired
    Match_ImageService matchImageService;
    @Autowired
    VideoUtil<Inquiry> videoUtil;
    @Autowired
    VideoService videoService;
    @Autowired
    CropService cropService;
    @Autowired
    SpectralService spectralService;

    @RequestMapping(value = "test", produces = "application/json")
    public CommonResult test(){
        return new CommonResult(200,"OK");
    }

    //问诊信息增加
    @PostMapping(value = "/add", produces = "application/json")
    public CommonResult InquiryAdd( @RequestParam("userid") int userid,
                                    @RequestParam("symptom") String symptom,
                                    @RequestParam("spectral") int spectral,
                                   @RequestParam("plantname") String plantname,
                                   // 胁迫图像，设置两张图片
                                   @PathVariable(value = "image1", required = false) MultipartFile image1,
                                   @PathVariable(value = "image2", required = false) MultipartFile image2,
                                   // 胁迫视频
                                   @PathVariable(value = "video", required = false) MultipartFile video) throws NotImageException {
        Inquiry inquiry = new Inquiry();
        inquiry.setSymptom(symptom);
        inquiry.setUserid(userid);
        inquiry.setSpectral(spectral);
        List<MultipartFile> multipartFiles1 = new ArrayList<>();
                // 1.1. 添加情报图像法
                multipartFiles1.add(image1);
                multipartFiles1.add(image2);
                List<Image> images1;
                Video video1;
                try {
                    // 1.3 将情报图像插入
                    images1 = imageUtil.dealImage(multipartFiles1, inquiry, new InquiryTranslation());
                    List<Integer> imageIds1 = imageService.addImageList(images1);
                    List<Match_Image> match_images1 = new ArrayList<>();
                    Match_Image matchImage;
                    for (int imageId : imageIds1) {
                        matchImage = new Match_Image(imageId, "胁迫图像");
                        match_images1.add(matchImage);
                    }
                    int images = matchImageService.addListImage(match_images1);
                    inquiry.setImage(images);

                     //视频上传
                    video1 = videoUtil.dealVideo(video, inquiry, new InquiryVideoTranslation());

                    Integer videoid = videoService.addVideo(video1);

                    inquiry.setVideo(videoid);

                    // 1.5 将plantid传给cropid
                    int plantid = cropService.findPlantId(plantname);
                    inquiry.setCropid(plantid);

                    // 设置时间为当前时间
                    Date date = new Date(System.currentTimeMillis());
                    inquiry.setTime(date);
                    inquiryService.add(inquiry.getUserid(), plantid, inquiry.getSymptom(), images, videoid, inquiry.getSpectral(), date);
                    log.info("问诊信息添加，问诊信息id为："+inquiry.getInquiryid());
                    return CommonResult.success(ResultCode.SUCCESS);
                } catch (DataException da) {
                    log.error("问诊信息添加异常,id为："+inquiry.getInquiryid());
                    throw new DataException(da.getCode(), da.getMessage());
                } catch (NotImageException e) {
                    log.error("问诊信息情报表时，插入的不是图像");
                    throw new NotImageException(e.getCode(),e.getMessage());
                } catch (IOException e) {
                    e.getMessage();
                }
        return CommonResult.success(ResultCode.SUCCESS);
    }

    //问诊信息删除
    @ApiOperation(value = "问诊信息删除功能")
    @PostMapping(value = "/delete", produces = "application/json")
    public CommonResult InquiryDelete(@RequestParam("inquiryid") Integer inquiryid) {
        try {
            inquiryService.delete(inquiryid);
            log.info("删除id为："+inquiryid+"的问诊信息");
            return CommonResult.success(ResultCode.SUCCESS);
        } catch (DataException dde) {
            log.error("不存在id为："+inquiryid+"的问诊信息，删除失败");
            throw new DataException(dde.getCode(), dde.getMessage());
        }
    }


    //问诊信息查询功能
    @ApiOperation(value = "问诊信息查询功能")
    @PostMapping(produces = "application/json")
    public CommonResult<PageInfo<Inquiry>> InquiryFind(int page, int pagesize) {
        try {
            PageInfo<Inquiry> inquiries = inquiryService.selectAll(page,pagesize);
           log.info("查询所有的问诊信息");
            return CommonResult.success(inquiries);
        } catch (DataException dfe) {
            log.error("问诊信息表中没有数据");
            throw new DataException(dfe.getCode(), dfe.getMessage());
        }
    }

    //问诊信息修改
    @ApiOperation(value = "问诊信息修改功能")
    @PostMapping(value = "/modify", produces = "application/json")
    public CommonResult InquiryModify(Inquiry inquiry) {
        try {
            Integer modify = inquiryService.modify(inquiry);
            log.info("问诊信息更新，更新id为："+inquiry.getInquiryid());
            return CommonResult.success(modify);
        } catch (DataException due) {
            log.error("更新id = "+inquiry.getInquiryid()+"的问诊信息失败");
            throw new DataException(due.getCode(), due.getMessage());
        }
    }
}
