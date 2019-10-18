package cn.farm.service;

import cn.farm.entity.Model;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ModelService {
    void add(String modelname, Integer author, String filepath, String description);

    void delete(Integer modelid);

    PageInfo<Model> selectAll(int page, int pagesize);

    Integer modify(Model model);
}
