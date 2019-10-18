package cn.farm.login.service;

import cn.farm.login.exception.DefaultException;
import cn.farm.login.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    public List<String> foundByType(String type, String value) {
        List<String> list = null;
        if(type.equals("kingdom")){
            list = categoryMapper.foundByType1(value);
        }else if(type.equals("phylum")){
            list = categoryMapper.foundByType2(value);
        }else if (type.equals("_class")){
            list = categoryMapper.foundByType3(value);
        }
        if(list.isEmpty()){
            throw new DefaultException("未找到任何相关信息");
        }
        return list;
    }
}
