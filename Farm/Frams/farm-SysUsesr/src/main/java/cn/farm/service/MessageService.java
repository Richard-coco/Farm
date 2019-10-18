package cn.farm.service;

import cn.farm.entity.Message;
import com.github.pagehelper.PageInfo;

import java.util.Date;
import java.util.List;

public interface MessageService {
     void add(String title, String keyword, String author, Date date, String webpage);

    void delete(Integer messid);

    PageInfo<Message> selectAll(int page, int pagesize);

    Integer modify(Message message);
}
