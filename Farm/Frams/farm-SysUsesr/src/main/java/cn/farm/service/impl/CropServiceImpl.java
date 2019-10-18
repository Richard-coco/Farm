package cn.farm.service.impl;

import cn.farm.entity.Biologycategory;
import cn.farm.entity.Plant;
import cn.farm.exception.*;
import cn.farm.mapper.*;
import cn.farm.service.CropService;
import cn.farm.utils.CommonResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.xml.crypto.Data;
import java.util.List;

@Service
public class CropServiceImpl implements CropService {
    @Autowired
    private BiologyMapper biologyMapper;
    @Autowired
    private BiologyMapperSql biologyMapperSql;
    @Autowired
    private PlantMapper plantMapper;
    @Autowired
    private PlantMapperSql plantMapperSql;

    @Override
    @Transactional
    public void add(Integer matchid,String kingdom, String phylum, String bio_class, String order,
                       String family, String genus, String species,
                       String chinesename, String latinname,
                       String alias, String distribution, String morphology, String growthhabit){
        //先添加bio表，添加植物就意味这bio表中没有这个植物信息
        try {
            Biologycategory biologycategory;
            //1.先查询数据库中
            biologycategory = new Biologycategory(kingdom, phylum, bio_class, order, family, genus, species);
            // 2. 查询数据库中是否存在，如果存在则不插入，反之则插入
            Integer bio = biologyMapperSql.selectBio(biologycategory);
            if (bio == null) {
                biologyMapper.insert(biologycategory);
                // 3. 如果不存在则先插入数据再根据species查询bioID
                Example selectBio = new Example(Biologycategory.class);
                selectBio.createCriteria().andEqualTo("species", species);    //"species"为数据库中的字段，species为前端传来的字段，两者进行比较
                List<Biologycategory> list = biologyMapper.selectByExample(selectBio);
                biologycategory = list.get(0);
            } else {
                // 4. 如果存在数据则直接根据species查询bioID
                Example selectBio = new Example(Biologycategory.class);
                selectBio.createCriteria().andEqualTo("species", species);    //"species"为数据库中的字段，species为前端传来的字段，两者进行比较
                List<Biologycategory> list = biologyMapper.selectByExample(selectBio);
                biologycategory = list.get(0);
            }

            Plant plant;
            plant = new Plant(chinesename, latinname, alias, biologycategory.getBioid(), distribution, morphology,matchid, growthhabit);
            Integer p = plantMapper.insert(plant);
            if (p == 0) {
                throw new DataException("400", "植物添加失败");
            }
        } catch (DataException dae){
            throw new DataException(dae.getCode(),dae.getMessage());
        }
    }



    /*
     * 删除作物信息功能
     * */
    @Override
    @Transactional  //添加事务注解，若出现错误则回滚数据
    public int deletePlant(Integer plantid) {
        try {
            //1.首先查询数据库中是否有要删除的数据
          Plant plant =   plantMapper.selectByPrimaryKey(plantid);
          //如果删除的数据存在
          if (plant != null){
              return plantMapper.deleteByPrimaryKey(plantid);
          }else {
              throw new DataException("400","未找到该数据，删除失败");
          }
        } catch (DataException d) {
            throw new DataException(d.getCode(), d.getMessage());
        }
    }


    /*
    * 查询指定植物的数据
    * */
    @Override
    public Plant findSinglePlant(Integer plantid) {
        CommonResult commonResult;
        Plant plant = plantMapper.selectByPrimaryKey(plantid);
        //如果删除的数据存在
        if (plant != null) {
            return plant;
        }else {
            throw new DataException("400","数据不存在,查询失败");
        }
    }

    /*
    * 该方法专门根据植物名(plantname)查询plantID
    * */
    @Override
    public int findPlantId(String chinesename) {
        Integer plantid = plantMapperSql.findId(chinesename);
        if (plantid != null){
            return plantid;
        }else {
            throw new DataException("400","不存在该植物信息");
        }
    }

    /*
       * 修改作物信息
       * */
    @Transactional
    @Override
    public void updatePlant(Integer plantid, String chinesename, String latinname,
                            Integer category, String alias, String morphology,
                            String growthhabit, String distribution, Integer plantimage){
        //1.先根据id查询是否存在该数据
      Plant  plant = plantMapper.selectByPrimaryKey(plantid);
        //2.在修改数据里的内容
        // plantMapper.updateByPrimaryKeySelective(plant);
        if (plant != null){
          plantMapperSql.updatePlantMsg(plantid, chinesename, latinname, alias, category, distribution, morphology, plantimage, growthhabit);
        }else {
            throw  new DataException("4000","更新错误");
        }

    }

    /*
    * 修改作物名字
    * */
    @Transactional
    @Override
    public void updateNameById(Integer plantid) {
        //1.先根据id查询是否存在该数据
        Plant plant = plantMapper.selectByPrimaryKey(plantid);
        //2.在修改数据里的内容
        // plantMapper.updateByPrimaryKeySelective(plant);
        if (plant != null) {
            plantMapperSql.updateNameById(plantid);
        } else {
            throw new DataException("4000", "更新错误");
        }
    }

    // 分页
    @Override
    public PageInfo<Plant> getPlantPages(int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        List<Plant> plants = plantMapper.selectAll();
        PageInfo<Plant> pageInfoPlantList = new PageInfo<Plant>(plants);
        return pageInfoPlantList;
    }

}
