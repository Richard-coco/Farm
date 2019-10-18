package cn.farm.service;

import cn.farm.entity.Enemy;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface EnemyService {
    // 1. 添加天敌信息
    void add(String kingdom, String phylum, String bio_class, String order,
                    String family, String genus, String species,
                    String chinesename, String latinname,
                    String alias, String distribution, String morphology,Integer matchid,
                    String lifehabit);
   // 2. 删除天敌信息
   int delete(Integer enemyid);

   // 3. 分页查询所有天敌信息
   PageInfo<Enemy> findList(int page, int pagesize);

    void update(Enemy enemy);
}
