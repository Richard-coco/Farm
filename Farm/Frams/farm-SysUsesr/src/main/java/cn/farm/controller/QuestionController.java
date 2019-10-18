package cn.farm.controller;

import cn.farm.entity.*;
import cn.farm.exception.*;
import cn.farm.service.ImageService;
import cn.farm.service.Match_ImageService;
import cn.farm.service.QuestionService;
import cn.farm.service.UserService;
import cn.farm.utils.CommonResult;
import cn.farm.utils.ImageUtil;
import cn.farm.utils.ResultCode;
import cn.farm.utils.Translation.QuestionTranslation;
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
@Api(value = "farm/sysuser/question", tags = "咨询信息管理模块")  //描述信息用的，标注在类上
@RestController
@RequestMapping("farm/sysuser/question")
public class QuestionController {
    @Autowired
    QuestionService questionService;
    @Autowired
    UserService userService;
    @Autowired
    ImageUtil<Question> imageUtil;
    @Autowired
    ImageService imageService;
    @Autowired
    Match_ImageService matchImageService;

    @ApiOperation(value = "咨询添加功能")
    @PostMapping(value = "/add", produces = "application/json")
    public CommonResult QuestionAdd(Question question,
                                 @PathVariable(value = "file1", required = false) MultipartFile file1,
                                 @PathVariable(value = "file2", required = false) MultipartFile file2) throws NotImageException {
        List<MultipartFile> multipartFiles = new ArrayList<>();
        multipartFiles.add(file1);
        multipartFiles.add(file2);
        List<Image> images;
        // 1.将images信息插入进去
        try {
            // 1.1 先调用imageUtil.dealImage方法，处理插入的图片
            images = imageUtil.dealImage(multipartFiles, question, new QuestionTranslation());
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
            question.setImage(matchid);
            // 插入当前时间
            Date date = new Date(System.currentTimeMillis());
            question.setTime(date);
            // 4.将question表的信息插入进去
            questionService.add(question.getUserID(),question.getTitle(),question.getContent(),matchid,question.getDomain(),date);
            log.info("咨询信息添加，咨询id为："+question.getQuestid());
            return CommonResult.success(ResultCode.SUCCESS);
        } catch (NotImageException e) {
            throw new NotImageException(e.getCode(), e.getMessage());
        } catch (IOException e) {
            e.getMessage();
        }
        return CommonResult.success(ResultCode.SUCCESS);
    }

    //咨询信息删除
    @ApiOperation(value = "咨询信息删除功能")
    @PostMapping(value = "/delete", produces = "application/json")
    public CommonResult QuestionDelete(@RequestParam("questid") Integer questid) {
        try {
            questionService.delete(questid);
            log.info("删除id为："+questid+"的咨询信息");
            return CommonResult.success(ResultCode.SUCCESS);
        } catch (DataException d) {
            log.error("不存在id为："+questid+"的咨询信息，删除失败");
            throw new DataException(d.getCode(), d.getMessage());
        }
    }

    //咨询信息查询功能
        @ApiOperation(value = "咨询信息删除功能")
        @PostMapping(produces = "application/json")
        public CommonResult<PageInfo<Question>> QuestionFind(int page,int pagesize) {
            try {
                PageInfo<Question> questions = questionService.selectAll(page,pagesize);
                log.info("查询所有的咨询信息");
                return CommonResult.success(questions,"ok");
            } catch (DataException nd) {
                log.error("咨询信息表中没有数据");
                throw new DataException(nd.getCode(), nd.getMessage());
            }
        }

    //咨询信息修改
    @ApiOperation(value = "咨询信息修改功能")
    @PostMapping(value = "/modify", produces = "application/json")
    public CommonResult QuestionModify(Question question) {
        try {
            Integer modify = questionService.modify(question);
            log.info("咨询信息更新，更新id为："+ question.getQuestid());
            return CommonResult.success(modify);
        } catch (DataException u) {
            log.error("更新id = "+ question.getQuestid()+"的咨询信息失败");
            throw new DataException(u.getCode(), u.getMessage());
        }
    }
}
