package cn.farm.login.service;

import cn.farm.login.mapper.TpMapper;
import cn.farm.login.pojo.TpRegion2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class AddressService {
    @Autowired
    private TpMapper tpMapper;

    public List<TpRegion2> found(Integer level, Integer parentId) {
        List<TpRegion2> list = tpMapper.select(level,parentId);

        return list;
    }
}
