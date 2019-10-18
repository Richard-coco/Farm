package cn.farm.expert.mapper;

import cn.farm.expert.domain.Model;
import cn.farm.expert.utils.ModelProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ModelMapper {


    @Select("select * from model where author = #{id}")
    List<Model> findByAuthor(@Param("id") int author);

    @Select("select iF(author is null,0,author) from model where modelId = #{id}")
    int findByModelId(@Param("id") int modelId);

    @Insert("insert into `model` values (#{modelId},#{modelName},#{author},#{modelFile},#{description}); ")
    int add(Model model);

    @UpdateProvider(type = ModelProvider.class,method = "updateModel")
    int update(Model model);

    @Delete("delete from model where modelId = #{modelId} and author = #{author}")
     int remove(@Param("author") int author,@Param("modelId") int modelId);

    @Delete("delete from model where modelId = #{id}")
    int removeByModelId(@Param("id") int modelId);

}
