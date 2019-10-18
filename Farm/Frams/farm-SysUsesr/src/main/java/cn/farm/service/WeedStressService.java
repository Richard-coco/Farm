package cn.farm.service;

import cn.farm.entity.Weedstress;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface WeedStressService {
     void add(int plantid,String name, String maintype, String subtype, String factor,Integer bioid,String harm,String symptom,String control,int symptomImage);

     void delete(Integer weedid);

     PageInfo<Weedstress> selectAll(int page,int pagesize);

     Integer modify(Weedstress weedstress);
}
