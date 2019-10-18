package cn.farm.service.impl;

import cn.farm.entity.Analysis;
import cn.farm.exception.DataException;
import cn.farm.exception.DataException;
import cn.farm.exception.DataException;
import cn.farm.mapper.AnalysisMapper;
import cn.farm.mapper.InformationMapper;
import cn.farm.mapper.UserMapper;
import cn.farm.service.AnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AnalysisServiceImpl implements AnalysisService {
    @Autowired
    AnalysisMapper analysisMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    InformationMapper informationMapper;
    @Override
    public void add(Integer infoid,String analysis, Integer expert, Date date) {
        Analysis analysises = new Analysis(infoid,analysis, expert, date);
        try {
            Integer id = informationMapper.selectId(infoid);
            analysises.setInfoid(id);
            Integer userid = userMapper.selectId(expert);
            analysises.setExpert(userid);
            analysisMapper.insert(analysises);
        } catch (DataException da) {
            throw new DataException(da.getCode(), da.getMessage());
        }
    }

    @Override
    public void delete(Integer infoid) {
        try {
            // 1. 先查询数据库中是否存在该数据
            Integer id = analysisMapper.selectById(infoid);
            if (id != null) {
                // 1.1 如果存在则删除该条数据
               analysisMapper.delete(infoid);
            } else {
                // 1.2 如果不存在则抛异常，该数据不存在
                throw new DataException("400", "未找到该数据，删除失败");
            }
        } catch (DataException d) {
            throw new DataException(d.getCode(), d.getMessage());
        }

    }

    @Override
    public List<Analysis> selectAll() {
        return analysisMapper.selectAll();
    }

    @Override
    public Integer modify(Analysis analysis) {
        // 1. 在更新之前根据id查询是否有要查询的数据
        Integer id = analysisMapper.selectById(analysis.getInfoid());
        if (id != null) {
            // 2. 如果有则，更新数据
            return analysisMapper.modify(analysis);
        } else {
            // 3, 如果没有则报错
            throw new DataException("400", "数据不存在，不能更新");
        }
    }
}
