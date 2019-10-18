package cn.farm.service.impl;

import cn.farm.entity.Spectral;
import cn.farm.exception.DataException;
import cn.farm.exception.DataException;
import cn.farm.exception.DataException;
import cn.farm.mapper.SpectralMapper;
import cn.farm.service.SpectralService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class SpectralServiceImpl implements SpectralService {
    @Autowired
    SpectralMapper spectralMapper;

    @Transactional
    @Override
    public void add(String name, String format, String description, String author, Date date, String location, String device, String filepath) {
        Spectral spectral = new Spectral(name, format, description, author, date, location, device, filepath);
        try {
            // 1 添加之前1先查询数据库中是否存在该数据
            Integer spe = spectralMapper.select(spectral);
            if (spe != null) {
                // 2. 如果存在，则不插入
                throw new DataException("400", "存在该数据,请勿重新添加");
            } else {
                // 3. 如果不存在， 则插入
                spectralMapper.add(spectral);
            }
        } catch (DataException da) {
            throw new DataException(da.getCode(), da.getMessage());
        }
    }

    @Override
    public void delete(Integer spectralid) {
        try {

            // 1. 先查询数据库中是否存在该数据
            Integer id = spectralMapper.selectById(spectralid);
            if (id != null) {
                // 2. 如果存在，则根据id删除数据
                spectralMapper.delete(spectralid);
            } else {
                // 3. 如果不存在，则报错
                throw new DataException("400", "数据库中不存在该数据，删除异常");
            }
        } catch (DataException d) {
            throw new DataException(d.getCode(), d.getMessage());
        }
    }

    @Override
    public PageInfo<Spectral> selectAll(int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
              List<Spectral> spectrals = spectralMapper.selectAll();
              PageInfo<Spectral> pageInfoSpectralList = new PageInfo<>(spectrals);
              return pageInfoSpectralList;
    }

    @Transactional
    @Override
    public Integer modify(Spectral spectral) {
        try {
            // 1. 在更新之前根据id查询是否有要查询的数据
            Integer id = spectralMapper.selectById(spectral.getSpectralid());
            if (id != null) {
                // 2. 如果有则，更新数据
                return spectralMapper.modify(spectral);
            } else {
                // 3, 如果没有则报错
                throw new DataException("400", "数据不存在，不能更新");
            }
        } catch (DataException u) {
            throw new DataException(u.getCode(), u.getMessage());
        }
    }

}
