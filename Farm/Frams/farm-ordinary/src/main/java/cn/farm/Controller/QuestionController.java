package cn.farm.Controller;

import cn.farm.Conf.Exception.NotImageException;
import cn.farm.Conf.Exception.OnlyOneImageException;
import cn.farm.Entity.Image;
import cn.farm.Entity.MatchImage;
import cn.farm.Entity.Question;
import cn.farm.Entity.QuestionWithImage;
import cn.farm.MessgaeModel.Common;
import cn.farm.MessgaeModel.PageHelperMessage;
import cn.farm.Service.ImageService;
import cn.farm.Service.MatchImageService;
import cn.farm.Service.QuestionService;
import cn.farm.Util.ImageUtil;
import cn.farm.Util.QuestionWithImageHelper;
import cn.farm.Util.Translation.QuestionTranslation;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @Autowired
    ImageService imageService;

    @Autowired
    MatchImageService matchImageService;

    @Autowired
    ImageUtil<Question> imageUtil;


    //查询本人的咨询 咨询image不为空
    @GetMapping(value = "quest/select",produces = "application/problem+json;charset=UTF-8")
    public Common select( @RequestParam(value = "page",required = false,defaultValue = "1") int page,
                         @RequestParam(value = "pageSize",required = false,defaultValue = "2")int pageSize,int userID) {
        List<QuestionWithImage> questionWithImages ;
        PageInfo<Question> info;
        try {
            PageHelper.startPage(page,pageSize);
            questionWithImages = questionService.selectByUserID(userID);
            questionWithImages = QuestionWithImageHelper.addImageID(questionWithImages,matchImageService,imageService);
            info = new PageInfo(questionWithImages);
        } catch (Exception e) {
            e.printStackTrace();
            return new Common(500, "server error");
        }

        return new PageHelperMessage(200,"ok",info);

    }

    //查询所有咨询
    @GetMapping(value = "quest/selectAll",produces = "application/json")
    public Common selectAll(@RequestParam(value = "page",required = false,defaultValue = "1") int page,
                                @RequestParam(value = "pageSize",required = false,defaultValue = "2")int pageSize) {
        List<QuestionWithImage> questionWithImages;
        PageInfo<Question> info;
        try {
            PageHelper.startPage(page,pageSize);
            questionWithImages = questionService.selectAll();
            questionWithImages = QuestionWithImageHelper.addImageID(questionWithImages,matchImageService,imageService);
            info = new PageInfo(questionWithImages);

        } catch (Exception e) {
            e.printStackTrace();
            return new Common(500, "server error");
        }
        return new PageHelperMessage(200,"ok",info);
    }


    //加入咨询
    @PostMapping(value = "quest/insert",produces = "application/json")
    public Common insert(String title , String content , String domain , int userID,  @RequestParam(required = false,value ="file1") MultipartFile file1,
                         @RequestParam(required = false,value ="file2") MultipartFile file2, @RequestParam(required = false,value ="file3") MultipartFile file3) {
        Question question = new Question(domain,userID,title,content);
        //处理上传的图片(下载到本地)，获得处理好的List<Image>
        List<MultipartFile> multipartFileList = new ArrayList<>();
        multipartFileList.add(file1);
        multipartFileList.add(file2);
        multipartFileList.add(file3);
        List<Image> images;
        try {
            images = imageUtil.dealImage(multipartFileList, question, new QuestionTranslation());
        } catch (IOException e) {
            e.printStackTrace();
            return new Common(500,"server error");
        } catch (NotImageException e) {
            e.printStackTrace();
            return new Common(503,"插入的不是图片");
        }
        //将图片对象插入数据库，并构建matchImage List集合
        List<MatchImage> matchImages = new ArrayList<>();
        List<Integer> list = imageService.insertImageList(images);
        MatchImage matchImage;
        for (int i = 0; i < list.size(); i++) {
            matchImage = new MatchImage();
            matchImage.setImageID(list.get(i));
            matchImage.setMatchinfo("咨询信息表的咨询图片");
            matchImages.add(matchImage);
        }
        //matchImages对象插入数据库 获得matchID 封装到question中 完成咨询添加
        try{
            int key =  matchImageService.insertList(matchImages);
            question.setTime(new Date(System.currentTimeMillis()));
            if(key != 0){
                question.setImage(key);
                questionService.insert(question);
            }else {
               return new Common(505,"插入文件不存在");
            }
        }catch (Exception e){
            e.printStackTrace();
            return new Common(500,"server error");
        }

        return new Common(200, "ok");
    }

    //咨询修改
    @PostMapping(value = "quest/update",produces = "application/json")
    public Common update(String title, String content, @RequestParam("questID") int questID,int userID) {
        //查询咨询是否存在，如果存在判断是否有权限修改
        Question targetQuestion;
        if ((targetQuestion = questionService.selectByQuestID(questID)) == null) {
            return new Common(404, "undefined");
        }
        if (userID != targetQuestion.getUserID()) {
            return new Common(401, "unauthorized");
        }

        Date nowtime = new Date(System.currentTimeMillis());
        try {
            questionService.updateQuestion(questID,title,content,nowtime);
        } catch (Exception e) {
            e.printStackTrace();
            return new Common(500, "server error");
        }

        return new Common(200, "ok");
    }

    //添加图片
    //@IsLogin
    @PostMapping(value = "quest/insertImage",produces = "application/json")
    public Common insertImage(@RequestParam(required = false,value ="file") MultipartFile file,
                              @RequestParam("matchID") int matchID, @RequestParam(value = "questID")int questID,int userID){
        Question question;
        if((question = questionService.selectByQuestID(questID)) == null){
            return new Common(404,"undefined");
        }
        if(file == null || matchID == 0 || questID == 0){
            return new Common(400,"invalid request");
        }
        if(userID != question.getUserID()){
            return new Common(401,"unauthorized");
        }
        String path;
        Image image;
        MatchImage matchImage;
        try {
            path = imageUtil.downloadOneImage(file,question);
            image = new Image();
            image.setImageFile(path);
            image.setDescription(question.getContent());
            image.setName(question.getTitle());
            image.setTime(new Date(System.currentTimeMillis()));
            imageService.insert(image);
            matchImage = new MatchImage();
            matchImage.setImageID(image.getImageID());
            matchImage.setMatchinfo("咨询信息表的咨询图片");
            matchImage.setMatchID(matchID);
            matchImageService.insertWithMatchID(matchImage);
        } catch (IOException e) {
            e.printStackTrace();
            return new Common(500,"server error");
        }catch (Exception e){
            return new Common(500,"server error");
        }

        return new Common(200,"ok");
    }

    //删除图片
    @GetMapping(value = "quest/deleteImage",produces = "application/json")
    public Common deleteImage(HttpSession session, @RequestParam("imageID") int imageID , @RequestParam("questID") int questID,int userID){
        if(imageID == 0 || questID == 0 ){
            return new Common(400,"invalid request");
        }
        Question question;
        if((question = questionService.selectByQuestID(questID)) == null ){
            return new Common(404,"undefined");
        }
        if(userID != question.getUserID()){
            return new Common(400,"unauthorized");
        }
        //级联删除图片，必须保留一张图片确保matchID存在
        try {
            questionService.deleteImage(imageID,questID);
        } catch (OnlyOneImageException e) {
            e.printStackTrace();
            return new Common(405,"there is a image at least ");
        }catch (Exception e){
            e.printStackTrace();
            return new Common(500,"server error");
        }
        return new Common(200,"ok");
    }

    //咨询删除
    @GetMapping(value = "quest/delete",produces = "application/json")
    public Common delete(HttpSession session, int questID , int userID) {
        Question question;
        if ((question = questionService.selectByQuestID(questID)) == null) {
            return new Common(404, "undefined");
        }
        if (userID != question.getUserID()) {
            return new Common(400, "unauthorized");
        }
        try {
            questionService.deleteQuestion(questID);
        } catch (Exception e) {
            e.printStackTrace();
            return new Common(500, "server error");
        }
        return new Common(200, "ok");
    }

}
