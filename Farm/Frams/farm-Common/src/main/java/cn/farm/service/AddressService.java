package cn.farm.service;

import cn.farm.mapper.TpMapper;
import cn.farm.pojo.TpRegion2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class AddressService {
    @Autowired
    private TpMapper tpMapper;

    public List<TpRegion2> found(Integer level, Integer parentId) {
        Example example = new Example(TpRegion2.class);
        example.createCriteria().andEqualTo("level",level).andEqualTo("parentId",parentId);
        List<TpRegion2> list = tpMapper.selectByExample(example);

        return list;
    }
}
