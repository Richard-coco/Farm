package cn.farm.mapper;

import cn.farm.entity.Question;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionMapper {

    @Insert("insert into question(userID,title,content,image,domain,time) values(#{userid},#{title},#{content},#{image},#{domain},#{time})")
    void insert(Question question);

    @Select("select * from question where questID = #{questid}")
    Integer selectById(Integer questid);

    @Delete("delete from question where questID = #{questid}")
    void delete(Integer questid);

    @Select("select * from question")
    List<Question> selectAll();

    @Update("update question set userID = #{userid},title = #{title},content = #{content},image = #{image},domain = #{domain} where questID = #{questid}")
    Integer modify(Question question);
}
