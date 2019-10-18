package cn.farm.expert.mapper;

import cn.farm.expert.domain.*;
import cn.farm.expert.utils.AnswerProvider;
import cn.farm.expert.utils.InformationProvider;
import cn.farm.expert.utils.RuleProvider;
import org.apache.ibatis.annotations.*;
import java.util.List;

public interface ManagementMapper {



    @Select( "select * from information where author = #{param}")
    List<Information> findInformation(@Param("param") int param);

    @Insert("insert into `information` values (#{infoId},#{title},#{content},#{author}," +
            "#{time},#{location},#{images},#{videos});")
    int saveInformation(Information information);

    @Delete("delete from information where infoId = #{infoId} and author = #{author}")
    int removeInformation(@Param("infoId") int infoId,@Param("author") int author);

    @UpdateProvider(type = InformationProvider.class,method = "updateInformation")
    int updateInformation(Information information);


   @UpdateProvider(type = RuleProvider.class,method = "updateRule")
   int updateRule(Rule rule);

    @Select( "select * from rule where ruleId = #{id} and author = #{author}")
    List<Rule> findRule(@Param("id") int ruleId,@Param("author") int author);

    @Select( "select * from rule where author = #{author}")
    List<Rule> findRuleByAuthor(@Param("author") int author);

    @Insert("insert into `rule` values (#{ruleId},#{ruleName},#{author},#{stressType},#{cropId},#{ruleFile},#{description});")
    int saveRule(Rule rule);

    @Delete("delete from rule where ruleId = #{id} and author = #{author}")
    int removeRule(@Param("id") int ruleId,@Param("author") int author);


    /**
     * 根据需求 专家一般通过title查找问题
     * @param title
     * @return
     */
    @Select("select * from question where title = #{title}")
    List<Question> findQuestionByTitle(@Param("title") String title);


    /**
     * 查找专家问答过的问题
     * @param expertId
     * @return
     */
    @Select("select * from answer where expertId = #{id}")
    List<Answer> findAnswerByExpertId(@Param("id") int expertId);


    /**
     * 通过questId查找咨询信息
     * @param questId
     * @return
     */
    @Select("select * from question where questid = #{questid}")
    Question findQuestionByQuestId(@Param("questid") String questId);


    @Insert("insert into `answer` values (#{answerId},#{questId},#{expertId},#{content},#{time});")
    int saveAnswer(Answer answer);

    @UpdateProvider(type = AnswerProvider.class,method = "updateAnswer")
    int updateAnswer(Answer answer);

    @Delete("delete from answer where answerId = #{id} and expertId = #{author}")
    int removeAnswer(@Param("id") int answerId,@Param("author") int author);


}
