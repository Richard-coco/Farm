package cn.farm.expert.utils;

import cn.farm.expert.domain.Answer;
import org.apache.ibatis.jdbc.SQL;

public class AnswerProvider {

    public String updateAnswer(final Answer answer){

        return new SQL(){{
            UPDATE("answer");

            //条件写法.
            if(answer.getAnswerId()!= 0){
                SET("answerId=#{answerId}");
            }
            if(answer.getContent() != null) {
                SET("content = #{content}");
            }
            if(answer.getExpertId() != 0) {
                SET("expertId = #{expertId}");
            }
            if(answer.getQuestId() != 0) {
                SET("questId = #{questId}");
            }
            if(answer.getTime()!= null) {
                SET("time = #{time}");
            }
            WHERE("answerId=#{answerId} and expertId = #{expertId}");
        }}.toString();
    }
}
