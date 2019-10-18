package cn.farm.service.impl;

import cn.farm.entity.Protection;
import cn.farm.exception.DataException;
import cn.farm.exception.DataException;
import cn.farm.exception.DataException;
import cn.farm.mapper.ProtectionMapper;
import cn.farm.service.ProtectionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ProtectionServiceImpl implements ProtectionService {
    @Autowired
    ProtectionMapper protectionMapper;
    @Override
    public void add(String title, String keyword, String author,Date date, String webpage) {
        Protection protection = new Protection(title,keyword,author,date,webpage);
        // 1. 先查询数据库中是否有要插入的数据
        Integer pro = protectionMapper.select(protection);
        if (pro == null){
            // 3. 如果没有则插入
            protectionMapper.insert(protection);
        }else {
            // 2. 如果有，则不插入，提示数据库中存在该数据
            throw new DataException("400","数据库中存在该数据");
        }
    }

    @Override
    public void delete(Integer knowid) {
        // 1. 删除数据之前先查询数据库中是否有该数据
        Integer selectById = protectionMapper.selectById(knowid);
        if (selectById != null){
            // 2. 如果有，则删除该数据
            protectionMapper.delete(knowid);
        }else {
            // 3. 没有，则报错，提示该数据不存在
            throw new DataException("400","数据库中不存在该数据");
        }
    }

    @Override
    public PageInfo<Protection> selectAll(int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
              List<Protection> protections = protectionMapper.selectAll();
              PageInfo<Protection> pageInfoProtectionList = new PageInfo<>(protections);
              return pageInfoProtectionList;
    }

    @Transactional
    @Override
    public Integer modify(Protection protection) {
        // 1. 在更新之前根据id查询是否有要查询的数据
        Integer id = protectionMapper.selectById(protection.getKnowid());
        if (id != null) {
            // 2. 如果有则，更新数据
            return protectionMapper.modify(protection);
        } else {
            // 3, 如果没有则报错
            throw new DataException("400", "数据不存在，不能更新");
        }
    }


}
