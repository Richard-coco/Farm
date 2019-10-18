package cn.farm.expert.service.serviceImpl;

import cn.farm.expert.config.FilePathConfig;
import cn.farm.expert.domain.*;
import cn.farm.expert.exception.ZmException;
import cn.farm.expert.mapper.ImageMapper;
import cn.farm.expert.mapper.ManagementMapper;
import cn.farm.expert.mapper.MatchImageMapper;
import cn.farm.expert.service.ManagementService;
import cn.farm.expert.utils.MyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class ManagementServiceImpl implements ManagementService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ManagementMapper managementMapper;

    @Autowired
    private FilePathConfig filePathConfig;

    @Autowired
    private ImageMapper imageMapper;

    @Autowired
    private MatchImageMapper matchImageMapper;


    /**
     *
     * @param param1 列名
     * @param param2 参数1
     * @param userId  userID
     * @return 更新成功的行数
     */


    /**
     * 查询胁迫情报
     * @param author
     * @return
     */
    @Override
    public List<Information> findInformation(int author) {

        return managementMapper.findInformation(author);
    }

    /**
     * 添加胁迫情报
     * @param
     * @return
     */
    @Override
    public int saveInformation(String title1,
                               String content1,
                               int userId1,
                               String location1,
                               int videoPath1,
                               MultipartFile multipartFile) throws IOException,ZmException {
        //标题
        String title =  title1;
        //获取信息内容
        String content = content1;
        //获得提交者userId
        int userId = userId1;
        //获取地理位置
        String location = location1;
        //调用上传视频接口
        int videoPath = videoPath1;
        //没有图片传入路径为空
        String imagePath = "空";
        //没有图片传入1
        int matchId = 1;
        //文件名默认为空
        String imageName = "无";
        try {
            imagePath = MyUtils.uploadFile3(multipartFile,filePathConfig);

        if (imagePath.equals("图片为空")){
        }else{
            String contentType = multipartFile.getContentType();   //图片文件类型
            imageName = contentType.substring(contentType.indexOf("/") + 1);  // 获得文件后缀名称
            int randomNum = Integer.parseInt(("19"+ MyUtils.uuid8())); //此处需要19+随机数+日期算法生成唯一编号
            //添加image表信息
            Image image = new Image();
            image.setImageFile(imagePath);
            image.setDescription(title);
            image.setTime(new Date());
            image.setImageId(randomNum);
            image.setFormat(imageName);
            imageMapper.save(image);
            //添加matchImage表信息
            MatchImage matchImage = new MatchImage();
            matchImage.setImageId(randomNum);
            matchImage.setMatchinfo(title);
            matchImageMapper.add(matchImage);
            matchId = matchImageMapper.findMatchId(randomNum);
        }

        //添加Information表信息
        Information information = new Information();
        information.setTitle(title);
        information.setTime(new Date());   //设置添加信息时间
        information.setImages(matchId);
        information.setContent(content);
        information.setAuthor(userId);
        information.setVideos(videoPath);
        information.setLocation(location);

        int rows = managementMapper.saveInformation(information);
        return rows;
        }
        catch (IOException e) {
            throw new IOException();
        }
    }


    public int saveInformation1(String title,
                               String content,
                               int userId,
                               String location,
                               int videoPath,
                               MultipartFile [] multipartFiles) throws IOException,ZmException {

        int i = 0;

        int matchId = 1;

        //最多一次上传6张图片
        int [] arr = new int[6];

        for (MultipartFile multipartFile: multipartFiles) {

            if (!multipartFile.isEmpty()) {
                String path = MyUtils.uploadFile3(multipartFile, filePathConfig);
                String contentType = multipartFile.getContentType();   //图片文件类型
                String imageName = contentType.substring(contentType.indexOf("/") + 1);  // 获得文件后缀名称
                int randomNum = Integer.parseInt(("19"+ MyUtils.uuid8())); //此处需要19+随机数+日期算法生成唯一编号
                //添加图一信息到image表
                Image image = new Image();
                image.setImageFile(path);
                image.setDescription(title);
                image.setTime(new Date());
                image.setImageId(randomNum);
                image.setFormat(imageName);
                imageMapper.save(image);
                //将imageId存入数组arr
                arr[i] = randomNum;
                System.out.println(path);
                //上传图片数量
                i++;
            }
            System.out.println("图片数"+i);
        }
        if (i > 0) {
            //添加matchImage表信息
            MatchImage matchImage = new MatchImage();
            matchImage.setImageId(arr[0]);
            System.out.println(arr[0]+"数组");
            matchImage.setMatchinfo(title);
            matchImageMapper.add(matchImage);
            //取第一个作为matchId
            matchId = matchImageMapper.findMatchId(arr[0]);
            for (int a = 1; a < i; a++) {
                //添加matchImage表信息
                System.out.println(a);
                MatchImage matchImage1 = new MatchImage();
                matchImage1.setImageId(arr[a]);
                matchImage1.setMatchinfo(title);
                matchImage1.setMatchId(matchId);
                matchImageMapper.add(matchImage1);
                logger.info("11111");
            }
            logger.info("2222222");
        }
        //添加Information表信息
        Information information = new Information();
        information.setTitle(title);
        information.setTime(new Date());   //设置添加信息时间
        information.setImages(matchId);
        information.setContent(content);
        information.setAuthor(userId);
        information.setVideos(videoPath);
        information.setLocation(location);
        int rows = managementMapper.saveInformation(information);
        System.out.println(i);
        return rows;
    }

    /**
     * 删除自己录入的胁迫情报
     * @param infoId
     * @return
     */
    @Override
    public int removeInformation(int infoId,int author) {

        return managementMapper.removeInformation(infoId,author);
    }

    /**
     * 修改自己录入的胁迫情报
     * @param information
     * @return
     */
    @Override
    public int updateInformation(Information information) {

        return managementMapper.updateInformation(information);
    }


    @Override
    public int updateRule(Rule rule) {
        return managementMapper.updateRule(rule);
    }

    @Override
    public List<Rule> findRule(int ruleId,int author) {
        return managementMapper.findRule(ruleId,author);
    }

    @Override
    public List<Rule> findRule(int author) {
        return managementMapper.findRuleByAuthor(author);
    }

    @Override
    public int saveRule(Rule rule) {
        return managementMapper.saveRule(rule);
    }

    @Override
    public int removeRule(int ruleId,int author) {
        return managementMapper.removeRule(ruleId,author);
    }



    @Override
    public List<Question> findQuestionByTitle(String title) {
        return managementMapper.findQuestionByTitle(title);
    }

    @Override
    public List<Answer> findAnswerByExpertId(int id) {
        return managementMapper.findAnswerByExpertId(id);
    }

    @Override
    public int saveAnswer(Answer answer) {
        answer.setTime(new Date());
        return managementMapper.saveAnswer(answer);
    }

    @Override
    public int updateAnswer(Answer answer) {
        return managementMapper.updateAnswer(answer);
    }

    @Override
    public int removeAnswer(int answerId,int author) {
        return managementMapper.removeAnswer(answerId,author);
    }


}
