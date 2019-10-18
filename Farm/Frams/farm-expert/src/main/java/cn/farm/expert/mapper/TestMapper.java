package cn.farm.expert.mapper;

import cn.farm.expert.domain.Plant;
import org.apache.ibatis.annotations.*;

public interface TestMapper {

    /**
     * 级联查询测试
     * 通过查询出来的植物的属性级联查询一对一的biologycategory表的信息
     * @param chineseName
     * @return
     */
    @Select("select * from plant where chineseName = #{chinesename}")
    @Results({
            @Result(property = "categoryy", column = "category", one = @One(select =
                    "cn.farm.expert.mapper.ExpertMapper.findByCategory"))
    })
    Plant findByCNNameTest(@Param("chinesename") String chineseName);


}
