package cn.farm.service.impl;

import cn.farm.entity.Biologycategory;
import cn.farm.entity.Enemy;
import cn.farm.exception.*;
import cn.farm.mapper.BiologyMapper;
import cn.farm.mapper.BiologyMapperSql;
import cn.farm.mapper.EnemyMapper;
import cn.farm.service.EnemyService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class EnemyServiceImpl implements EnemyService {
    @Autowired
    BiologyMapper biologyMapper;
    @Autowired
    BiologyMapperSql biologyMapperSql;
    @Autowired
    EnemyMapper enemyMapper;

    @Transactional
    @Override
    public void add(String kingdom, String phylum, String bio_class, String order, String family,
                    String genus, String species, String chinesename, String latinname, String alias,
                    String distribution, String morphology, Integer matchid, String lifehabit) {
            // 1. 先添加bio表的信息
            Biologycategory biologycategory;
            biologycategory = new Biologycategory(kingdom, phylum, bio_class, order, family, genus, species);
            // 2. 查询数据库中是否存在该数据，如果不存在则添加，否则不添加
            Integer bio = biologyMapperSql.selectBio(biologycategory);
            if (bio == null) {
                biologyMapper.insert(biologycategory);
                // 3. 如果不存在则先插入数据再根据species查询bioID
                Example selectBio = new Example(Biologycategory.class);
                selectBio.createCriteria().andEqualTo("species", species);    //"species"为数据库中的字段，species为前端传来的字段，两者进行比较
                List<Biologycategory> list = biologyMapper.selectByExample(selectBio);
                biologycategory =  list.get(0);
            } else {
                // 4. 如果存在数据则直接根据species查询bioID
                Example selectBio = new Example(Biologycategory.class);
                selectBio.createCriteria().andEqualTo("species", species);    //"species"为数据库中的字段，species为前端传来的字段，两者进行比较
                List<Biologycategory> list = biologyMapper.selectByExample(selectBio);
               biologycategory = list.get(0);
            }

            Enemy enemy;
            enemy = new Enemy(chinesename, latinname, alias, biologycategory.getBioid(), distribution, morphology, matchid, lifehabit);
            int i = enemyMapper.insert(enemy);
            if (i == 0) {
                throw new DataException("400", "天敌信息添加失败");
            }

    }

    @Override
    @Transactional
    public int delete(Integer enemyid) {
        try{
            // 1. 先查询数据库中是否存在该数据
            Integer id = enemyMapper.selectById(enemyid);
            if (id != null){
                // 1.1 如果存在则删除该条数据
                return enemyMapper.delete(enemyid);
            }else {
                // 1.2 如果不存在则抛异常，该数据不存在
                throw new DataException("400","未找到该数据，删除失败");
            }
        }catch (DataException d){
            throw new DataException(d.getCode(),d.getMessage());
        }
    }

    @Override
    public PageInfo<Enemy> findList(int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        List<Enemy> enemies = enemyMapper.selectAll();
        PageInfo<Enemy> pageInfoPlantList = new PageInfo<Enemy>(enemies);
        return pageInfoPlantList;
    }

    @Transactional
    @Override
    public void update(Enemy enemy) {
        // 1. 先查询数据库中是否存在要查询的数据
        Integer id = enemyMapper.selectById(enemy.getEnemyid());
        if (id != null){
            // 2. 如果存在，则更新数据
            enemyMapper.update(enemy);
        }else {
            // 3. 如果不存在，则抛异常
            throw new DataException("400","数据不存在，不能更新");
        }

    }

}
