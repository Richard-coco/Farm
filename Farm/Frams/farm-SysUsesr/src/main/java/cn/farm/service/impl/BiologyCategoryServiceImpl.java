package cn.farm.service.impl;

import cn.farm.entity.Biologycategory;
import cn.farm.exception.DataException;
import cn.farm.mapper.BiologyMapper;
import cn.farm.mapper.BiologyMapperSql;
import cn.farm.service.BiologyCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BiologyCategoryServiceImpl implements BiologyCategoryService {
    @Autowired
    BiologyMapperSql biologyMapperSql;
    @Autowired
    BiologyMapper biologyMapper;

    @Override
    public int addBiologyCategory(String kingdom, String phylum, String bio_class, String order, String family, String genus, String species) {
        Biologycategory biologycategory;
        //1.先查询数据库中
        biologycategory = new Biologycategory(kingdom, phylum, bio_class, order, family, genus, species);
        Integer b = biologyMapperSql.insert(biologycategory);//插入bio数据
        if (b == 0) {
            throw new DataException("400", "bio表添加失败");
        }
         return biologyMapperSql.select(species);
    }

    @Override
    public int findBioId(Integer bioid) {
       return biologyMapperSql.selectById(bioid);
    }
}
