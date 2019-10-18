package cn.farm.service.impl;

import cn.farm.entity.Abioticstress;
import cn.farm.entity.Enemy;
import cn.farm.entity.Stresscategory;
import cn.farm.entity.Weedstress;
import cn.farm.exception.*;
import cn.farm.mapper.AbioticStressMapper;
import cn.farm.mapper.BiologyMapperSql;
import cn.farm.mapper.StressCategoryMapper;
import cn.farm.service.AbioticStressService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class AbioticStressServiceImpl implements AbioticStressService {
    @Autowired
    StressCategoryMapper stressCategoryMapper;
    @Autowired
    AbioticStressMapper abioticStressMapper;

    @Transactional
    @Override
    public void add(int plantid, String name, String maintype, String subtype, String factor, String harm, String symptom, String control, int symptomImage) {
            // 1. 先添加stresscategory的信息
            Stresscategory stresscategory;
            stresscategory = new Stresscategory(name, maintype, subtype, factor);
            // 查询数据库的stressCategory表
            Integer sc = stressCategoryMapper.select(stresscategory);
            // 1.1判断数据库中是否存在该信息
            if (sc == null) {
                // 1.2 如果不存在，则先插入数据在根据stresscategory查询id
                stressCategoryMapper.add(stresscategory);
                stresscategory = stressCategoryMapper.selectId(stresscategory);
            } else {
                // 1.3 如果存在，则直接根据stresscategory查询id
                stresscategory = stressCategoryMapper.selectId(stresscategory);
            }

            Abioticstress abioticstress;
            abioticstress = new Abioticstress(stresscategory.getStressid(),plantid,harm,symptom,control,symptomImage);
            // 2.1 先根据名字查询数据库中是否存在该草害胁迫信息
            Integer ps = abioticStressMapper.selectByHarmSymptom(abioticstress);
            if (ps == null) {
                // 2.2 如果不存在，则添加
                Integer add = abioticStressMapper.add(abioticstress);
                if (add == null) {
                    throw new DataException("500", "数据添加异常");
                }
            } else {
                // 2.3 如果存在，则报错
                throw new DataException("400", "该数据已存在，请勿重复添加");
            }


    }

    @Override
    public void delete(Integer abioticid) {
        // 1. 先查询数据库中是否存在该数据
        Integer id = abioticStressMapper.selectById(abioticid);
        if (id != null) {
            // 2. 如果存在，则根据id删除数据
            abioticStressMapper.delete(abioticid);
        } else {
            // 3. 如果不存在，则报错
            throw new DataException("400", "数据库中不存在该数据，删除异常");
        }
    }

    @Override
    public PageInfo<Abioticstress> selectAll(int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        List<Abioticstress> abioticstresses = abioticStressMapper.selectAll();
        PageInfo<Abioticstress> pageInfoAbioticstressList = new PageInfo<Abioticstress

                >(abioticstresses);
        return pageInfoAbioticstressList;
    }

    @Transactional
    @Override
    public Integer modify(Abioticstress abioticstress) {
        // 1. 在更新之前根据id查询是否有要查询的数据
        Integer id = abioticStressMapper.selectById(abioticstress.getAbioticid());

        if (id != null) {
            // 2. 如果有则，更新数据
            return abioticStressMapper.modify(abioticstress);
        } else {
            // 3, 如果没有则报错
            throw new DataException("400", "数据不存在，不能更新");
        }
    }
}
