package cn.farm.service;

import cn.farm.entity.Spectral;
import com.github.pagehelper.PageInfo;

import java.util.Date;
import java.util.List;

public interface SpectralService {
    void add(String name, String format, String description, String author, Date date, String location, String device, String filepath);

    void delete(Integer spectralid);

    PageInfo<Spectral> selectAll(int page, int pagesize);

    Integer modify(Spectral spectral);
}
