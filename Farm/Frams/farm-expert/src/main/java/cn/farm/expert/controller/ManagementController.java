package cn.farm.expert.controller;

import cn.farm.expert.config.FilePathConfig;
import cn.farm.expert.domain.*;
import cn.farm.expert.service.ManagementService;
import cn.farm.expert.utils.MyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/farm/expert/")
public class ManagementController {

    @Autowired
    private ManagementService managementService;

    @Autowired
    private FilePathConfig filePathConfig;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 查询自己录入的胁迫情报
     * @return
     */
    @GetMapping(value = "information/find",produces = "application/json")
    public Data findInformation(@RequestParam(value = "userid",required = true) int userId,
                                @RequestParam(value = "pagesize",defaultValue = "3") int  pageSize,
                                @RequestParam(value = "pagenum",defaultValue = "1")int pageNum) {
        //打印日志
        logger.info("userId:" + userId + " 进行了查找操作");
        List<Information> list;
        list = managementService.findInformation(userId);
        Data data = MyUtils.findTo(list, logger, pageSize, pageNum);
        return data;
    }
    /**
     * 添加胁迫情报
     * @param
     * @return 添加成功数
     */
    @PostMapping(value = "information/add",produces = "application/json")
    public Data saveInformation(@RequestParam(value = "title",required = false,defaultValue = "无标题")String  title1,
                                @RequestParam(value = "userid",required = true) int userId,
                                @RequestParam(value = "file",required = false) MultipartFile multipartFile,
                                @RequestParam(value = "content",required = false,defaultValue = "内容为空")String content1){
        int sum = 0;
        try {

        String title = title1;
        String content = content1;
        //打印日志
        logger.info("userId:"+userId+" 进行了添加操作");
        //先写死 ，需要获取地理位置接口
        String location = "北京";
        //先写死，调用视频上传接口后获得
        int videoPath = 1;
        sum = managementService.saveInformation(title,content,userId,location,videoPath,multipartFile);
        }
        catch (IOException e) {
            //打印日志
           logger.error("图片上传出错了");
           Data data = new Data();
           data.setCode(500);
           data.setMsg("server error");
           return data;
        }catch (Exception e){
            //打印日志
            logger.error("添加出错了");
            Data data = new Data();
            data.setCode(500);
            data.setMsg("server error");
            return data;
        }
        //打印日志
        logger.info("添加成功条数为"+sum);
        Data data = new Data();
        JsonData1 jsonData1 = new JsonData1();
        jsonData1.setData(sum);
        data.setData(jsonData1);
        data.setCode(201);
        data.setMsg("created");
        return data;
    }

    /**
     * 删除自己录入的胁迫情报
     * @param infoId
     * @return
     */
    @GetMapping(value = "information/remove",produces = "application/json")
    public Data removeInformation(@RequestParam(value = "infoid",required = true) int infoId,@RequestParam(value = "userid",required = true) int userId){

            logger.info("userId:"+userId+" 进行了删除操作");
            int sum = managementService.removeInformation(infoId,userId);
            Data data = MyUtils.removeTo(sum,logger);
            return data;
    }

    /**
     * 更新录入的胁迫情报
     * @param title
     * @param userId
     * @param multipartFile
     * @param content
     * @return
     */
    @PostMapping(value = "information/update",produces = "application/json")
    public  Data updateInformation(@RequestParam(value = "title",required = false,defaultValue = "无标题")String  title,
                                   @RequestParam(value = "userid",required = true) int userId,
                                   @RequestParam(value = "file",required = false) MultipartFile multipartFile,
                                   @RequestParam(value = "content",required = false,defaultValue = "内容为空")String content
                                   ) throws IOException {

            //打印日志
            logger.info("userId:"+userId+" 进行了更新操作");
            Information information = new Information();
            information.setContent(content);
            information.setAuthor(userId);
            information.setTitle(title);
            int sum = managementService.updateInformation(information);
            Data data = MyUtils.updateTo(logger,sum);
            return data;
    }

    /**
     * 保留接口
     * 查询获得的情报按照地理位置信息显示的地图上，点击蓝色图标可以查看胁迫情报的概要信息，点击标题可以进入胁迫情报的详情页面。
     * 专家可以根据胁迫情报的上传时间和地理分布，分析胁迫程度，据此对防治工作进行决策部署，发布胁迫预警。
     */
    @PostMapping(value = "operating/map",produces = "application/json")
    public void operatingMap(){

        //打印日志
        logger.info("operating/map接口还未开发");
    }


    /**
     * 查询诊断规则
     * @return
     */
    @GetMapping(value = "rule/find",produces = "application/json")
    public  Data findRule(@RequestParam(value = "pagesize",defaultValue = "3") int  pageSize,
                             @RequestParam(value = "pagenum",defaultValue = "1")int pageNum,
                             @RequestParam(value = "userid",required = true) int userId){

            logger.info("userId:"+userId+" 进行了查询");
            List<Rule> list;
            list = managementService.findRule(userId);
            Data data = MyUtils.findTo(list,logger,pageSize,pageNum);
            return data;
    }

    /**
     * 添加诊断规则
     * @param ruleName
     * @param userId
     * @param stressType
     * @param cropId
     * @param file
     * @param description
     * @return
     */
    @PostMapping(value = "rule/save",produces = "application/json")
    public Data saveRule( @RequestParam(value = "rulename",required = true) String ruleName,
                          @RequestParam(value = "userid",required = true) int userId,
                          @RequestParam(value = "stresstype",required = true)int stressType,
                          @RequestParam(value = "cropid",required = true)int cropId,
                          @RequestParam(value = "rulefile",required = false)MultipartFile file,
                          @RequestParam(value = "description",required = false)String description){

            logger.info("userId:"+userId+" 进行了添加操作");
            String path = MyUtils.uploadFile(file,filePathConfig.getModelpath());
            Rule rule = new Rule();
            rule.setAuthor(userId);
            rule.setCropId(cropId);
            rule.setDescription(description);
            rule.setRuleFile(path);
            rule.setRuleName(ruleName);
            rule.setStressType(stressType);
            int sum = managementService.saveRule(rule);
            //打印日志
            Data data = MyUtils.saveTo(logger,sum);
            return data;
    }

    /**
     * 删除诊断规则
     * @param ruleId
     * @return
     */
    @GetMapping(value = "rule/remove",produces = "application/json")
    public Data removeRule(@RequestParam(value = "ruleid",required = true) int ruleId,
                           @RequestParam(value = "userid",required = true) int userId){

            logger.info("userId:"+userId+" 进行了删除操作");
            int sum = managementService.removeRule(ruleId,userId);
            Data data = MyUtils.removeTo(sum,logger);
            return data;
    }

    /**
     * 更新诊断规则
     * rule
     * @return
     */
    @PostMapping(value = "rule/update",produces = "application/json")
    public Data updateRule(@RequestParam(value = "ruleid",required = true) int ruleId ,
                           @RequestParam(value = "rulename",required = false) String ruleName,
                           @RequestParam(value = "stresstype",required = false)int stressType,
                           @RequestParam(value = "cropid",required = false)int cropId,
                           @RequestParam(value = "rulefile",required = false)MultipartFile file,
                           @RequestParam(value = "description",required = false)String description,
                           @RequestParam(value = "userid",required = true) int userId
                            ){

            logger.info("userId:"+userId+" 进行了更新操作");
            Rule rule = new Rule();
            rule.setStressType(stressType);
            rule.setRuleName(ruleName);
            rule.setDescription(description);
            rule.setCropId(cropId);
            rule.setAuthor(userId);
            int sum = managementService.updateRule(rule);
            Data data = MyUtils.updateTo(logger,sum);
            return data;

    }
/*

    */
/**
     * 查询问题
     * @param title
     * @param pageSize
     * @param pageNum
     * @return
     *//*

    @GetMapping("questionbytitle/find")
    public JsonData findQuestionByTitle(@RequestParam(value = "title") String title,
                                        @RequestParam(value = "pagesize",defaultValue = "3") int  pageSize,
                                        @RequestParam(value = "pagenum",defaultValue = "1")int pageNum){
        JsonData jsonData = new JsonData();
        Data data = new Data();
       return jsonData.selectSuccess(managementService.findQuestionByTitle(title),pageNum,pageSize);
    }
*/

    /**
     * 查询自己回答的问题
     * @param pageSize
     * @param pageNum
     * @return
     */
    @GetMapping(value = "answerbyexpertid/find",produces = "application/json")
    public Data findAnswerByExpertId(@RequestParam(value = "userid",required = true) int userId,
                                     @RequestParam(value = "pagesize",defaultValue = "3") int  pageSize,
                                     @RequestParam(value = "pagenum",defaultValue = "1")int pageNum){

        logger.info("userId:"+userId+" 进行了查询");
        List list= managementService.findAnswerByExpertId(userId);
        Data data = MyUtils.findTo(list,logger,pageSize,pageNum);
        return data;
    }

    /**
     * 回答问题
     * @param questId
     * @param content
     * @return
     */
    @PostMapping(value = "answer/save",produces = "application/json")
    public Data saveAnswer(@RequestParam(value = "userid",required = true) int userId,
                           @RequestParam(value = "questid",required = true)int questId,
                           @RequestParam(value = "content",required = true)String content,
                           @RequestParam(value = "answerid",required = true) int answerId) {

            logger.info("userId:"+userId+" 进行了添加操作");
            Answer answer = new Answer();
            answer.setTime(new Date());
            answer.setQuestId(questId);
            answer.setExpertId(userId);
            answer.setContent(content);
            answer.setAnswerId(answerId);
            int sum = managementService.saveAnswer(answer);
            Data data =  MyUtils.saveTo(logger,sum);
            return data;
    }

    /**
     *修改问题
     * answer
     * @return
     */
    @PostMapping(value = "answer/update",produces = "application/json")
    public Data updateAnswer(@RequestParam(value = "questid",required = true)int questId,
                             @RequestParam(value = "content",required = false)String content,
                             @RequestParam(value = "userid",required = true) int userId,
                             @RequestParam(value = "answerid",required = true) int answerId) {

           logger.info("userId:"+userId+" 进行了更新操作");
           Answer answer = new Answer();
           answer.setAnswerId(userId);
           answer.setTime(new Date());
           answer.setContent(content);
           answer.setAnswerId(answerId);
           int sum = managementService.updateAnswer(answer);
           Data data = MyUtils.updateTo(logger,sum);
           return data;
    }

    /**
     * 删除问题
     * @param answerId
     * @return
     */
    @GetMapping(value = "answer/remove",produces = "application/json")
    public Data removeAnswer(@RequestParam("answerid") int answerId,
                             @RequestParam(value = "userid",required = true) int userId){

            logger.info("userId:"+userId+" 进行了删除操作");
            int sum = managementService.removeAnswer(answerId,userId);
            Data data = MyUtils.removeTo(sum,logger);
            return data;
    }


    }

