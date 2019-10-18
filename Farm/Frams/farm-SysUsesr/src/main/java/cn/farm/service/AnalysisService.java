package cn.farm.service;

import cn.farm.entity.Analysis;

import java.util.Date;
import java.util.List;

public interface AnalysisService {
    void add(Integer inforid,String analysis, Integer expert, Date date);

    void delete(Integer infoid);

    List<Analysis> selectAll();

    Integer modify(Analysis analysis);
}
