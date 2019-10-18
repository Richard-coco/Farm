package cn.farm.service.impl;

import cn.farm.entity.Diagnosis;
import cn.farm.exception.DataException;
import cn.farm.exception.DataException;
import cn.farm.mapper.*;
import cn.farm.service.DiagnosisService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiagnosisServiceImpl implements DiagnosisService {
    @Autowired
    InquiryMapper inquiryMapper;
    @Autowired
    RuleMapper ruleMapper;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    StressCategoryMapper stressCategoryMapper;
    @Autowired
    DiagnosisMapper diagnosisMapper;
    @Override
    public void add(Integer inquiryid, Integer ruleid, Integer modelid, Integer type, Integer degree) {
        Diagnosis diagnosis = new Diagnosis(inquiryid,ruleid,modelid,type,degree);
        try {
            // 1，插入inquiryID
            Integer inqid = inquiryMapper.selectById(inquiryid);
            diagnosis.setInquiryid(inqid);
            // 2. 插入ruleid
            Integer ruid = ruleMapper.selectById(ruleid);
            diagnosis.setRuleid(ruid);
            // 3. 插入modelID
            Integer moid = modelMapper.selectById(modelid);
            // 4. 插入stresscategoryId
            Integer strid = stressCategoryMapper.selectById(type);
            diagnosis.setType(strid);
            diagnosisMapper.insert(diagnosis);
        }catch (DataException da){
            throw new DataException(da.getCode(),da.getMessage());
        }
    }

    @Override
    public void delete(Integer inquiryid) {
            // 1. 先查询数据库中是否存在该数据
            Integer id = diagnosisMapper.selectById(inquiryid);
            if (id != null) {
                // 1.1 如果存在则删除该条数据
                diagnosisMapper.delete(inquiryid);
            } else {
                // 1.2 如果不存在则抛异常，该数据不存在
                throw new DataException("400", "未找到该数据，删除失败");
            }
    }

    @Override
    public PageInfo<Diagnosis> getDiagnosisPages(int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        List<Diagnosis> diagnosis = diagnosisMapper.selectAll();
        PageInfo<Diagnosis> pageInfoDiagnosisList = new PageInfo<Diagnosis>(diagnosis);
        return pageInfoDiagnosisList;
    }
}
