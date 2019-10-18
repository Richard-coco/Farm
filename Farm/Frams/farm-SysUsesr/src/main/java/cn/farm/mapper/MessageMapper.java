package cn.farm.mapper;

import cn.farm.entity.Message;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MessageMapper {

    @Select("select * from message where title = #{title} and keyword = #{keyword} and author = #{author} and webpage = #{webpage}")
    Integer select(Message message);

    @Insert("insert into message(title,keyword,author,date,webpage) values(#{title},#{keyword},#{author},#{date},#{webpage})")
    void insert(Message message);

    @Select("select * from message where messID = #{messid}")
    Integer selectById(Integer messid);

    @Delete("delete from message where messID = #{messid}")
    void delete(Integer messid);

    @Select("select * from message")
    List<Message> selectAll();

    @Update("update message set title = #{title},keyword = #{keyword},author = #{author},webpage = #{webpage} where messID = #{messid}")
    Integer modify(Message message);
}
