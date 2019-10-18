package cn.farm.service.impl;

import cn.farm.entity.Information;
import cn.farm.exception.DataException;
import cn.farm.exception.DataException;
import cn.farm.exception.DataException;
import cn.farm.mapper.InformationMapper;
import cn.farm.mapper.UserMapper;
import cn.farm.service.InformationService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class InformationServiceImpl implements InformationService {
    @Autowired
    InformationMapper informationMapper;
    @Autowired
    UserMapper userMapper;

    @Transactional
    @Override
    public void add(String title, String content, Integer author, Date date, String location, int images, int videos) {
        Information information = new Information(title, content, author, date, location, images, videos);
        try {
            Integer id = userMapper.selectId(author);
            if (id !=null ){
                information.setAuthor(id);
                informationMapper.add(information);

            }else {
                throw new DataException("400","存在该数据，添加失败");
            }
        } catch (DataException da) {
            throw new DataException(da.getCode(), da.getMessage());
        }
    }

    @Override
    public void delete(Integer infoid) {
        // 1. 删除数据之前先查询数据库中是否有该数据
        Integer selectById = informationMapper.selectById(infoid);
        if (selectById != null) {
            // 2. 如果有，则删除该数据
            informationMapper.delete(infoid);
        } else {
            // 3. 没有，则报错，提示该数据不存在
            throw new DataException("400", "数据库中不存在该数据");
        }
    }

    @Override
    public PageInfo<Information> selectAll(int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        List<Information> information = informationMapper.selectAll();
        PageInfo<Information> pageInfoPlantList = new PageInfo<Information>(information);
        return pageInfoPlantList;
    }

    @Transactional
    @Override
    public Integer modify(Information information) {
        // 1. 在更新之前根据id查询是否有要查询的数据
               Integer id = informationMapper.selectById(information.getInfoid());
               System.out.println(id);
               if (id != null) {
                   // 2. 如果有则，更新数据
                   return informationMapper.modify(information);
               } else {
                   // 3, 如果没有则报错
                   throw new DataException("400", "数据不存在，不能更新");
               }
    }
}
