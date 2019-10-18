package cn.farm.service;

import cn.farm.entity.Abioticstress;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface AbioticStressService {
    void add(int plantid, String name, String maintype, String subtype, String factor, String harm, String symptom, String control, int symptomImage);

    void delete(Integer abioticid);

    PageInfo<Abioticstress> selectAll(int page,int pagesize);

    Integer modify(Abioticstress abioticstress);
}
