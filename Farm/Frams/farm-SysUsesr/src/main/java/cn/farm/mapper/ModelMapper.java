package cn.farm.mapper;

import cn.farm.entity.Model;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModelMapper {
    @Insert("insert into model(modelName,author,modelFile,description) values(#{modelname},#{author},#{modelfile},#{description})")
    @Options(useGeneratedKeys = true,keyProperty = "modelid",keyColumn = "modelID")
    void insert(Model model);

    @Select("select * from model where modelID = #{modelid}")
    Integer selectById(Integer modelid);

    @Delete("delete from model where modelID = #{modelid}")
    void delete(Integer modelid);

    @Select("select * from model")
    List<Model> selectAll();

    @Update("update model set modelName = #{modelname},author = #{author},modelFile = #{modelfile},description = #{description} where modelID = #{modelid}")
    Integer modify(Model model);
}
