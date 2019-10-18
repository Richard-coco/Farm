package cn.farm.service;
import cn.farm.entity.Plant;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface CropService {
    void add(Integer matchid,String kingdom, String phylum, String bio_class, String order,
                String family, String genus, String species,
                 String chinesename, String latinname,
                String alias, String distribution, String morphology, String growthhabit);

    int deletePlant(Integer plantid);


    Plant findSinglePlant(Integer plantid);

    int findPlantId(String chinesename);

    void updatePlant(Integer plantid, String chinesename, String latinname, Integer  category, String alias, String morphology,
                     String growthhabit, String distribution, Integer plantimage);
     void updateNameById(Integer plantid);

    PageInfo<Plant> getPlantPages(int page, int pagesize);
}
