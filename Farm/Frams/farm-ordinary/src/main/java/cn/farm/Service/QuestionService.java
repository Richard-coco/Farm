package cn.farm.Service;


import cn.farm.Conf.Exception.OnlyOneImageException;
import cn.farm.Entity.Question;
import cn.farm.Entity.QuestionWithImage;
import cn.farm.Mapper.ImageMapper;
import cn.farm.Mapper.MatchImageMapper;
import cn.farm.Mapper.QuestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.Iterator;
import java.util.List;

@Service
@Transactional
public class QuestionService {

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    MatchImageMapper matchImageMapper;

    @Autowired
    ImageMapper imageMapper;


    public List<QuestionWithImage> selectByUserID(int userID){
        return questionMapper.select(userID);
    }

    public List<QuestionWithImage> selectAll(){
        return  questionMapper.selectAll() ;
    }

    public Question selectByQuestID(int questID){
        return questionMapper.selectByQuestID(questID);
    }

    public void insert(Question question) {
        questionMapper.insert(question);
    }

    public void delete(int questID){
        questionMapper.delete(questID);
    }

    public void update(Question question){
        questionMapper.update(question);
    }

    public void updateQuestion(int questID , String title ,String content , Date date){
        questionMapper.updateQuestion(questID,title,content,date);
    }

    public void deleteImage(int imageID , int questID) throws OnlyOneImageException {

        int matchID = questionMapper.selectImageByQuestID(questID);
        if(matchImageMapper.selectCountByMatchID(matchID)<2){
            throw new OnlyOneImageException();
        }
        questionMapper.emptyImage(questID);
        matchImageMapper.deleteByImageID(imageID);
        imageMapper.deleteByImageID(imageID);
        questionMapper.backImage(questID,matchID);
    }

    public void deleteQuestion(int questID){
        int matchID = questionMapper.selectImageByQuestID(questID);
        questionMapper.emptyImage(questID);
        List<Integer> list = matchImageMapper.selectByMatchID(matchID);
        matchImageMapper.deleteByMatchID(matchID);
        Iterator<Integer> iterator = list.iterator();
        while(iterator.hasNext()){
            imageMapper.deleteByImageID(iterator.next());
        }
        questionMapper.delete(questID);
    }
}
