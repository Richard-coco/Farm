package cn.farm.login.mapper;

import cn.farm.login.pojo.Diseasestress;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface DiseaseMapper extends Mapper<Diseasestress> {
    @Select("select * from diseasestress where chineseName like CONCAT('%',#{name},'%')")
    List<Diseasestress> likeSelect(@Param(value = "name") String name);
}
