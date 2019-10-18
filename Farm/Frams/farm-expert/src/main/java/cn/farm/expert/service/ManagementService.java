package cn.farm.expert.service;
import cn.farm.expert.domain.*;
import cn.farm.expert.exception.ZmException;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

public interface ManagementService {


   List<Information> findInformation(int author);

   int saveInformation(String title1,String content1,int userId1,String location1,int videoPath1,
                       MultipartFile multipartFile) throws IOException, ZmException;



   int removeInformation(int infoId,int author);

   int updateInformation(Information information);



   int  updateRule(Rule rule);

   List<Rule> findRule(int ruleId,int author);

   List<Rule> findRule(int author);

   int saveRule(Rule rule);

   int removeRule(int ruleId,int author);


   List<Question> findQuestionByTitle(String title);

   List<Answer> findAnswerByExpertId(int title);

   int saveAnswer(Answer answer);

   int updateAnswer(Answer answer);

   int removeAnswer(int answerId,int author);


   public int saveInformation1(String title1,
                               String content1,
                               int userId1,
                               String location1,
                               int videoPath1,
                               MultipartFile [] multipartFiles) throws IOException,ZmException;

}
