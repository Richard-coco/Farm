package cn.farm.service;

import cn.farm.entity.Protection;
import com.github.pagehelper.PageInfo;

import java.util.Date;
import java.util.List;

public interface ProtectionService {
    void add(String title, String keyword, String author, Date date, String webpage);

    void delete(Integer knowid);

    PageInfo<Protection> selectAll(int page, int pagesize);

    Integer modify(Protection protection);
}
