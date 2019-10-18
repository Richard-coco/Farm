package cn.farm.mapper;

import cn.farm.entity.Rule;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RuleMapper {

    @Insert("insert into rule(ruleName,author,stressType,cropID,ruleFile,description) values(#{rulename},#{author},#{stresstype},#{cropid},#{rulefile},#{description})")
    @Options(useGeneratedKeys = true,keyProperty = "ruleid",keyColumn = "ruleID")
    void insert(Rule rule);

    @Select("select * from rule where ruleID = #{ruleid}")
    Integer selectById(Integer ruleid);

    @Delete("delete from rule where ruleID = #{ruleid}")
    void delete(Integer ruleid);

    @Select("select * from rule")
    List<Rule> selectAll();

    @Update("update rule set ruleName = #{rulename},author = #{author},stressType = #{stresstype},cropID = #{cropid},ruleFile = #{rulefile},description = #{description} where ruleID = #{ruleid}")
    Integer modify(Rule rule);
}
