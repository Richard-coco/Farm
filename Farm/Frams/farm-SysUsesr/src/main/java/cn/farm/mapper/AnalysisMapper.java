package cn.farm.mapper;

import cn.farm.entity.Analysis;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnalysisMapper {
    @Select("select * from analysis where infoID = #{infoid}")
    Integer selectId(Integer infoid);

    @Insert("insert into analysis(infoID,analysis,expert,time) values(#{infoid},#{analysis},#{expert},#{time})")
    void insert(Analysis analysises);

    @Select("select * from analysis where infoID = #{infoid}")
    Integer selectById(Integer infoid);

    @Delete("delete from analysis where infoID = #{infoid}")
    void delete(Integer infoid);

    @Select("select * from analysis")
    List<Analysis> selectAll();

    @Update("update analysis set analysis = #{analysis},expert = #{expert} where infoID = #{infoid}")
    Integer modify(Analysis analysis);
}
