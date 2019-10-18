package cn.farm.mapper;

import cn.farm.entity.Plant;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;


@Repository
public interface PlantMapper extends Mapper<Plant> {
}
