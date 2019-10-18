package cn.farm.service.impl;

import cn.farm.entity.Diseasestress;
import cn.farm.entity.Enemy;
import cn.farm.entity.Stresscategory;
import cn.farm.exception.*;
import cn.farm.mapper.DiseaseStressMapper;
import cn.farm.mapper.StressCategoryMapper;
import cn.farm.service.DiseaseStressService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DiseaseStressServiceImpl implements DiseaseStressService {
    @Autowired
    DiseaseStressMapper diseaseStressMapper;
    @Autowired
    StressCategoryMapper stressCategoryMapper;

    @Transactional
    @Override
    public void add(Integer plantid,String chinesename, String latinname, String alias, String distribution,
                    String symptom, String pathogeny, String occurrence, String control,
                    Integer pathoImage, Integer symptomImage, String name,String maintype, String subtype,
                    String factor) {
        try {
            // 1. 先添加stresscategory的信息
            Stresscategory stresscategory;
            stresscategory = new Stresscategory(name,maintype,subtype,factor);
            // 查询数据库的stressCategory表
            Integer sc = stressCategoryMapper.select(stresscategory);
            // 1.1判断数据库中是否存在该信息
            if (sc == null) {
                // 1.2 如果不存在，则先插入数据在根据stresscategory查询id
                stressCategoryMapper.add(stresscategory);
                stresscategory = stressCategoryMapper.selectId(stresscategory);
              //  System.out.println(stresscategory);
            } else {
                // 1.3 如果存在，则直接根据stresscategory查询id
                stresscategory = stressCategoryMapper.selectId(stresscategory);
              //  System.out.println(stresscategory);
            }
            // 2 插入diseaseStress
            Diseasestress diseasestress;
            diseasestress = new Diseasestress(stresscategory.getStressid(),plantid,chinesename,latinname,alias,distribution,symptom,pathogeny,occurrence,
                    control,pathoImage,symptomImage);
            // 2.1 先根据名字查询数据库中是否存在该病害胁迫信息
            Integer ds = diseaseStressMapper.selectByName(diseasestress);
            if (ds == null){
                // 2.2 如果不存在，则添加
                Integer add = diseaseStressMapper.add(diseasestress);
                if (add == null){
                    throw new DataException("500","数据添加异常");
                }
            }else {
                // 2.3 如果存在，则报错
                throw new DataException("400","该数据已存在，请勿重复添加");
            }
        }catch (DataException dae){
            throw new DataException(dae.getCode(),dae.getMessage());
        }
    }

    @Override
    public void delete(Integer diseaseid) {
        // 1. 先查询数据库中是否存在该数据
        Integer id = diseaseStressMapper.selectById(diseaseid);
        if (id != null){
            // 2. 如果存在，则根据id删除数据
            diseaseStressMapper.delete(diseaseid);
        }else {
            // 3. 如果不存在，则报错
            throw new DataException("400","数据库中不存在该数据，删除异常");
        }
    }

    @Override
    public PageInfo<Diseasestress> selectAll(int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        List<Diseasestress> diseasestresses = diseaseStressMapper.selectAll();
        PageInfo<Diseasestress> pageInfoPlantList = new PageInfo<Diseasestress>(diseasestresses);
        return pageInfoPlantList;
    }

    @Transactional
    @Override
    public Integer modify(Diseasestress diseasestress) {
        // 1. 在更新之前根据id查询是否有要查询的数据
        Integer id = diseaseStressMapper.selectById(diseasestress.getDiseaseid());
        if (id != null){
            // 2. 如果有则，更新数据
            return diseaseStressMapper.modify(diseasestress);
        }else {
            // 3, 如果没有则报错
            throw new DataException("400","数据不存在，不能更新");
        }
    }
}
