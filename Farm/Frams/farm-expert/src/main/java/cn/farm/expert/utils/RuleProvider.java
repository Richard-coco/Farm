package cn.farm.expert.utils;
import cn.farm.expert.domain.Rule;
import org.apache.ibatis.jdbc.SQL;

public class RuleProvider {

    public String updateRule(final Rule rule){

        return new SQL(){{
            UPDATE("rule");

            //条件写法.
            if(rule.getRuleName() !=null){
                SET("ruleName=#{ruleName}");
            }
            if(rule.getAuthor() != 0) {
                SET("author = #{author}");
            }
            if(rule.getStressType() != 0) {
                SET("stressType = #{stressType}");
            }
            if(rule.getCropId() != 0) {
                SET("cropId = #{cropId}");
            }
            if(rule.getRuleFile()!= null) {
                SET("ruleFile = #{ruleFile}");
            }
            if(rule.getRuleId()!= 0) {
                SET("ruleId = #{ruleId}");
            }
            if(rule.getDescription() != null) {
                SET("description = #{description}");
            }
            WHERE("ruleId=#{ruleId} and author = #{author}");
        }}.toString();
    }
}
