package cn.farm.service;

import cn.farm.entity.Stressfeature;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface StressFeatureService {
    void add(Integer stresstype, String paramfile, String vectfile);

    void delete(Integer stessid);

    PageInfo<Stressfeature> selectAll(int page, int pagesize);

    Integer modify(Stressfeature stressfeature);
}
