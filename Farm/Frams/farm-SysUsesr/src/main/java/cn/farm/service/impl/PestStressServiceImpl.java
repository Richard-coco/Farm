package cn.farm.service.impl;

import cn.farm.entity.Peststress;
import cn.farm.entity.Plant;
import cn.farm.entity.Stresscategory;
import cn.farm.exception.*;
import cn.farm.mapper.BiologyMapperSql;
import cn.farm.mapper.PestStressMapper;
import cn.farm.mapper.StressCategoryMapper;
import cn.farm.service.PestStressService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PestStressServiceImpl implements PestStressService {
    @Autowired
    PestStressMapper pestStressMapper;
    @Autowired
    StressCategoryMapper stressCategoryMapper;
    @Autowired
    BiologyMapperSql biologyMapperSql;

    @Transactional
    @Override
    public void add(int plantid, String chinesename, String latinname, String alias,Integer bioid, String distribution, String morphology, String lifehabit, String damage, String control, int pestImage, int syptomImage, String name, String maintype, String subtype, String factor) {
        try {
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
            // 2 插入pestStress
            Peststress peststress;
            peststress = new Peststress(stresscategory.getStressid(), plantid, chinesename, latinname, alias,bioid, distribution, morphology, lifehabit, damage,
                    control, pestImage, syptomImage);
            // 2.1 先根据名字查询数据库中是否存在该虫害胁迫信息
            Integer ps = pestStressMapper.selectByName(peststress);
            if (ps == null) {
                // 2.2 如果不存在，则添加
                Integer add = pestStressMapper.add(peststress);
                if (add == null) {
                    throw new DataException("500", "数据添加异常");
                }
            } else {
                // 2.3 如果存在，则报错
                throw new DataException("400", "该数据已存在，请勿重复添加");
            }
        } catch (DataException dae) {
            throw new DataException(dae.getCode(), dae.getMessage());
        }
    }

    @Override
    public void delete(Integer pestid) {
        // 1. 先查询数据库中是否存在该数据
        Integer id = pestStressMapper.selectById(pestid);
        if (id != null) {
            // 2. 如果存在，则根据id删除数据
            pestStressMapper.delete(pestid);
        } else {
            // 3. 如果不存在，则报错
            throw new DataException("400", "数据库中不存在该数据，删除异常");
        }
    }

    @Override
    public PageInfo<Peststress> selectAll(int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        List<Peststress> peststresses = pestStressMapper.selectAll();
        PageInfo<Peststress> pageInfoPestList = new PageInfo<Peststress>(peststresses);
        return pageInfoPestList;
    }

    @Transactional
    @Override
    public Integer modify(Peststress peststress) {
        // 1. 在更新之前根据id查询是否有要查询的数据
        Integer id = pestStressMapper.selectById(peststress.getPestid());
        System.out.println(id);
        if (id != null) {
            // 2. 如果有则，更新数据
            return pestStressMapper.modify(peststress);
        } else {
            // 3, 如果没有则报错
            throw new DataException("400", "数据不存在，不能更新");
        }
    }
}

