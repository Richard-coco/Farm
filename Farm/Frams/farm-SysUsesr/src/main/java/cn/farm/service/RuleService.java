package cn.farm.service;

import cn.farm.entity.Rule;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface RuleService {
    void add(String rulename, Integer author, Integer stressId, int plantid, String filepath, String description);

    void delete(Integer ruleid);

    PageInfo<Rule> selectAll(int page, int pagesize);

    Integer modify(Rule rule);
}
