package cn.farm.service;

import cn.farm.entity.Peststress;
import com.github.pagehelper.PageInfo;


public interface PestStressService {
   void add(int plantid, String chinesename, String latinname, String alias,Integer bioid, String distribution,
                    String morphology, String lifehabit, String damage, String control, int pestImage, int syptomImage,
                    String name, String maintype, String subtype, String factor);

   void delete(Integer pestid);

   PageInfo<Peststress> selectAll(int page, int pagesize);

   Integer modify(Peststress peststress);
}
