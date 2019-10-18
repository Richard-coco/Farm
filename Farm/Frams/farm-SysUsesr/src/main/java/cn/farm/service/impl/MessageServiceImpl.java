package cn.farm.service.impl;

import cn.farm.entity.Message;
import cn.farm.exception.DataException;
import cn.farm.exception.DataException;
import cn.farm.exception.DataException;
import cn.farm.mapper.MessageMapper;
import cn.farm.service.MessageService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    MessageMapper messageMapper;

    @Transactional
    @Override
    public void add(String title, String keyword, String author, Date date, String webpage) {
        Message message = new Message(title, keyword, author, date, webpage);
        // 1. 先查询数据库中是否有要插入的数据
        Integer pro = messageMapper.select(message);
        if (pro == null) {
            // 3. 如果没有则插入
            messageMapper.insert(message);
        } else {
            // 2. 如果有，则不插入，提示数据库中存在该数据
            throw new DataException("400", "数据库中存在该数据");
        }
    }

    @Override
    public void delete(Integer messid) {
        // 1. 删除数据之前先查询数据库中是否有该数据
        Integer selectById = messageMapper.selectById(messid);
        if (selectById != null) {
            // 2. 如果有，则删除该数据
            messageMapper.delete(messid);
        } else {
            // 3. 没有，则报错，提示该数据不存在
            throw new DataException("400", "数据库中不存在该数据");
        }
    }

    @Override
    public PageInfo<Message> selectAll(int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        List<Message> messages = messageMapper.selectAll();
        PageInfo<Message> pageInfoMmesssageList = new PageInfo<>(messages);
        return pageInfoMmesssageList;
    }

    @Transactional
    @Override
    public Integer modify(Message message) {
        // 1. 在更新之前根据id查询是否有要查询的数据
        Integer id = messageMapper.selectById(message.getMessid());
        if (id != null) {
            // 2. 如果有则，更新数据
            return messageMapper.modify(message);
        } else {
            // 3, 如果没有则报错
            throw new DataException("400", "数据不存在，不能更新");
        }
    }
}
