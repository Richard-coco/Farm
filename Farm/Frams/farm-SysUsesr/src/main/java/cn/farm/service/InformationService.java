package cn.farm.service;

import cn.farm.entity.Information;
import com.github.pagehelper.PageInfo;

import java.util.Date;

public interface InformationService {
    void add(String title, String content, Integer author, Date date, String location, int images, int videos);

    void delete(Integer infoid);

    PageInfo<Information> selectAll(int page,int pagesize);

    Integer modify(Information information);
}
