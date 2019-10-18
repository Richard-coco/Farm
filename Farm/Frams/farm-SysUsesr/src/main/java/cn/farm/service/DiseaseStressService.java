package cn.farm.service;

import cn.farm.entity.Diseasestress;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface DiseaseStressService {

    void add(Integer plantid,String chinesename,String latinname,String alias,String distribution,String symptom,
             String pathogeny,String occurrence,String control,Integer pathoImage,Integer symptomImage,
             String name,String maintype,String subtype,String factor);

    void delete(Integer diseaseid);

    PageInfo<Diseasestress> selectAll(int page, int pagesize);

    Integer modify(Diseasestress diseasestress);
}
