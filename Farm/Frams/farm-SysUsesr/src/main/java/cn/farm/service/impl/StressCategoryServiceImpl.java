package cn.farm.service.impl;

import cn.farm.mapper.StressCategoryMapper;
import cn.farm.service.StressCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StressCategoryServiceImpl implements StressCategoryService {
    @Autowired
    StressCategoryMapper stressCategoryMapper;
    @Override
    public Integer findStressId(String stressname) {
        Integer stressid = stressCategoryMapper.selectByName(stressname);
        return stressid;
    }
}
