package cn.farm.Mapper;


import cn.farm.Entity.Question;
import cn.farm.Entity.QuestionWithImage;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.sql.Date;
import java.util.List;

public interface QuestionMapper  {

    @Select("select * from question where userID = #{userID}")
    List<QuestionWithImage> select(int userID);

    @Select("select * from question")
    List<QuestionWithImage> selectAll();

    @Select("select * from question where questID = #{questID}")
    Question selectByQuestID(int questID);

    @Select("select image from question where questID = #{questID}")
    int selectImageByQuestID(int questID);

    @Insert("insert into question(userID,title,content,image,domain,time) values(#{userID},#{title},#{content},#{image},#{domain},#{time})")
    int insert(Question question);

    @Update("update question set " +
            "title = case when #{title} is not null then #{title} else title end," +
            "content = case when #{content} is not null then #{content} else content end," +
            "domain = case when #{domain} is not null then #{domain} else domain end," +
            "image = case when #{image} is not null then #{image} else image end,"+
            "time = case when #{time} is not null then #{time} else time end" +
            " where questID = #{questID}")
    int update(Question question);

    @Update("update question set image = null where questID = #{questID}")
    void emptyImage(int questID);

    @Update("update question set image = #{matchID} where questID = #{questID}")
    void backImage(int questID, int matchID);

    @Update("update question set title = case when #{title} is not null then #{title} else title end ," +
            "content = case when #{content} is not null then #{content} else content end," +
            "time = case when #{date} is not null then #{date} else time end " +
            "where questID = #{questID}")
    void updateQuestion(int questID, String title, String content, Date date);

    @Delete("delete from question where questID = #{questID}")
    int delete(int questID);


}
