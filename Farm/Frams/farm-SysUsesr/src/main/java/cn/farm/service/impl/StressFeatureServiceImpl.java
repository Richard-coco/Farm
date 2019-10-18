package cn.farm.service.impl;

import cn.farm.entity.Stressfeature;
import cn.farm.exception.DataException;
import cn.farm.exception.DataException;
import cn.farm.exception.DataException;
import cn.farm.mapper.StressFeatureMapper;
import cn.farm.service.StressFeatureService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StressFeatureServiceImpl implements StressFeatureService {
    @Autowired
    StressFeatureMapper stressFeatureMapper;

    @Transactional
    @Override
    public void add(Integer stresstype, String paramfile, String vectfile) {
        Stressfeature stressfeature = new Stressfeature(stresstype, paramfile, vectfile);
        try {
            // 1 添加之前1先查询数据库中是否存在该数据
            Integer spe = stressFeatureMapper.select(stressfeature);
            if (spe != null) {
                // 2. 如果存在，则不插入
                throw new DataException("400", "存在该数据,请勿重新添加");
            } else {
                // 3. 如果不存在， 则插入
                stressFeatureMapper.add(stressfeature);
            }
        } catch (DataException da) {
            throw new DataException(da.getCode(), da.getMessage());
        }
    }

    @Override
    public void delete(Integer stessid) {
        try {
            // 1. 先查询数据库中是否存在该数据
            Integer id = stressFeatureMapper.selectById(stessid);
            if (id != null) {
                // 2. 如果存在，则根据id删除数据
                stressFeatureMapper.delete(stessid);
            } else {
                // 3. 如果不存在，则报错
                throw new DataException("400", "数据库中不存在该数据，删除异常");
            }
        } catch (DataException d) {
            throw new DataException(d.getCode(), d.getMessage());
        }
    }

    @Override
    public PageInfo<Stressfeature> selectAll(int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
              List<Stressfeature> stressfeatures = stressFeatureMapper.selectAll();
              PageInfo<Stressfeature> pageInfoStressfeatureList = new PageInfo<>(stressfeatures);
              return pageInfoStressfeatureList;
    }

    @Override
    public Integer modify(Stressfeature stressfeature) {
            // 1. 在更新之前根据id查询是否有要查询的数据
            Integer id = stressFeatureMapper.selectById(stressfeature.getStessid());
            if (id != null) {
                // 2. 如果有则，更新数据
                return stressFeatureMapper.modify(stressfeature);
            } else {
                // 3, 如果没有则报错
                throw new DataException("400", "数据不存在，不能更新");
            }

    }
}
