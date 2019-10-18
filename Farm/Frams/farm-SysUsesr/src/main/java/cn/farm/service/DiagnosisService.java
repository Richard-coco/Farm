package cn.farm.service;

import cn.farm.entity.Diagnosis;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface DiagnosisService {
    void add(Integer inquiryid, Integer ruleid, Integer modelid, Integer type, Integer degree);

    void delete(Integer inquiryid);


    PageInfo<Diagnosis> getDiagnosisPages(int page, int pagesize);
}
