package cn.farm.service;

import cn.farm.entity.Question;
import com.github.pagehelper.PageInfo;

import java.util.Date;
import java.util.List;

public interface QuestionService {
     void add(Integer userId, String title, String content, int matchid, String domain, Date date);

    void delete(Integer questid);

    PageInfo<Question> selectAll(int page, int pagesize);

    Integer modify(Question question);
}
