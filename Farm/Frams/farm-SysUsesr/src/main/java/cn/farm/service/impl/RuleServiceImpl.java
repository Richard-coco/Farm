package cn.farm.service.impl;

import cn.farm.entity.Rule;
import cn.farm.exception.DataException;
import cn.farm.exception.DataException;
import cn.farm.exception.DataException;
import cn.farm.mapper.RuleMapper;
import cn.farm.mapper.UserMapper;
import cn.farm.service.RuleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RuleServiceImpl implements RuleService {
   @Autowired
   RuleMapper ruleMapper;
   @Autowired
    UserMapper userMapper;
   @Transactional
    @Override
    public void add(String rulename, Integer author, Integer stressId, int plantid, String filepath, String description) {
        Rule rule = new Rule(rulename,author,stressId, plantid,filepath,description);
        try{
            Integer id = userMapper.selectId(author);
            rule.setAuthor(id);
           //直接插入数据
            ruleMapper.insert(rule);

        }catch (DataException da){
            throw new DataException(da.getCode(),da.getMessage());
        }
    }

    @Override
    public void delete(Integer ruleid) {
        // 1. 先查询数据库中是否存在该数据
        Integer id = ruleMapper.selectById(ruleid);
        if (id != null) {
            // 2. 如果存在，则根据id删除数据
            ruleMapper.delete(ruleid);
        } else {
            // 3. 如果不存在，则报错
            throw new DataException("400", "数据库中不存在该数据，删除异常");
        }
    }

    @Override
    public PageInfo<Rule> selectAll(int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        List<Rule> rules = ruleMapper.selectAll();
        PageInfo<Rule> pageInfoRuleList = new PageInfo<>(rules);
        return pageInfoRuleList;
    }

    @Transactional
    @Override
    public Integer modify(Rule rule) {
        // 1. 在更新之前根据id查询是否有要查询的数据
        Integer id = ruleMapper.selectById(rule.getRuleid());
        if (id != null) {
            // 2. 如果有则，更新数据
            return ruleMapper.modify(rule);
        } else {
            // 3, 如果没有则报错
            throw new DataException("400", "数据不存在，不能更新");
        }
    }
}
