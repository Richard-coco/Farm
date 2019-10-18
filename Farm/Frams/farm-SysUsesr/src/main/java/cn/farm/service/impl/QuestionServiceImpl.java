package cn.farm.service.impl;

import cn.farm.entity.Question;
import cn.farm.exception.DataException;
import cn.farm.exception.DataException;
import cn.farm.exception.DataException;
import cn.farm.mapper.QuestionMapper;
import cn.farm.mapper.UserMapper;
import cn.farm.service.QuestionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    UserMapper userMapper;

    @Transactional
    @Override
    public void add(Integer userId, String title, String content, int matchid, String domain, Date date) {
        Question question = new Question(userId,title,content,matchid,domain,date);
        try {
            Integer id = userMapper.selectId(userId);
            question.setUserid(id);
            questionMapper.insert(question);
        }catch (DataException da){
            throw new DataException(da.getCode(),da.getMessage());
        }
    }

    @Override
    public void delete(Integer questid) {
        // 1. 先查询数据库中是否存在该数据
        Integer id = questionMapper.selectById(questid);
        if (id != null) {
            // 2. 如果存在，则根据id删除数据
            questionMapper.delete(questid);
        } else {
            // 3. 如果不存在，则报错
            throw new DataException("400", "数据库中不存在该数据，删除异常");
        }
    }

    @Override
    public PageInfo<Question> selectAll(int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
          List<Question> questions = questionMapper.selectAll();
          PageInfo<Question> pageInfoQuestionList = new PageInfo<>(questions);
          return pageInfoQuestionList;
    }

    @Transactional
    @Override
    public Integer modify(Question question) {
        // 1. 在更新之前根据id查询是否有要查询的数据
        Integer id = questionMapper.selectById(question.getQuestid());
        if (id != null) {
            // 2. 如果有则，更新数据
            return questionMapper.modify(question);
        } else {
            // 3, 如果没有则报错
            throw new DataException("400", "数据不存在，不能更新");
        }

    }

}
