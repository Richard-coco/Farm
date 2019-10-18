package cn.farm.service.impl;

import cn.farm.entity.Model;
import cn.farm.exception.DataException;
import cn.farm.exception.DataException;
import cn.farm.exception.DataException;
import cn.farm.mapper.ModelMapper;
import cn.farm.mapper.UserMapper;
import cn.farm.service.ModelService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class ModelServiceImpl implements ModelService {
   @Autowired
   ModelMapper modelMapper;
   @Autowired
    UserMapper userMapper;
    @Override
    public void add(String modelname, Integer author, String filepath, String description) {
        Model model = new Model(modelname,author,filepath,description);
        try {
            Integer id = userMapper.selectId(author);
            model.setAuthor(id);
            //直接插入数据
            modelMapper.insert(model);
        } catch (DataException da) {
            throw new DataException(da.getCode(), da.getMessage());
        }
    }

    @Override
    public void delete(Integer modelid) {
        // 1. 先查询数据库中是否存在该数据
        Integer id = modelMapper.selectById(modelid);
        if (id != null) {
            // 2. 如果存在，则根据id删除数据
            modelMapper.delete(modelid);
        } else {
            // 3. 如果不存在，则报错
            throw new DataException("400", "数据库中不存在该数据，删除异常");
        }
    }

    @Override
    public PageInfo<Model> selectAll(int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        List<Model> models = modelMapper.selectAll();
        PageInfo<Model> pageInfoModelList = new PageInfo<>(models);
        return pageInfoModelList;
    }

    @Transactional
    @Override
    public Integer modify(Model model) {
        // 1. 在更新之前根据id查询是否有要查询的数据
        Integer id = modelMapper.selectById(model.getModelid());
        if (id != null) {
            // 2. 如果有则，更新数据
            return modelMapper.modify(model);
        } else {
            // 3, 如果没有则报错
            throw new DataException("400", "数据不存在，不能更新");
        }
    }


}
