package cn.farm.login.service;

import cn.farm.login.dto.*;
import cn.farm.login.enums.CropStateEnum;
import cn.farm.login.exception.CategoryException;
import cn.farm.login.exception.LikeException;
import cn.farm.login.exception.NotFoundException;
import cn.farm.login.mapper.CategoryMapper;
import cn.farm.login.mapper.ImageMapper;
import cn.farm.login.mapper.MatchImageMapper;
import cn.farm.login.mapper.PlantMapper;
import cn.farm.login.pojo.Biologycategory;
import cn.farm.login.pojo.Image;
import cn.farm.login.pojo.MatchImage;
import cn.farm.login.pojo.Plant;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class PlantService {
    @Autowired
    private PlantMapper plantMapper;
    @Autowired
    private ImageMapper imageMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private MatchImageMapper matchImageMapepr;

    public Result<PageHelperResult> foundByName(String name, Integer pageNum, Integer pageSize) {
        //声明数据变量
        Result<PageHelperResult> result;//返回信息
        CropExecution execution;//返回Data
        PageHelperResult<Plant> pageHelperResult = new PageHelperResult<>();
        //List<Plant> list1 = new ArrayList<>();
        try{
            //查询所有数据

            List<Plant> list = plantMapper.likeSelect(name);
            Integer all = list.size();

            //查询数据库
            PageHelper.startPage(pageNum,pageSize);//从第几条数据开始，每页显示多少条数据
            list = plantMapper.likeSelect(name);
            pageHelperResult.setList(list);
            pageHelperResult.setNumber(all);
            if(list.size() == 0){
                throw new LikeException("查询不到任何信息");
            }
            
            //构造返回变量
            execution = new CropExecution(CropStateEnum.SUCCESS);
            result = new Result(execution.getState(),pageHelperResult,execution.getStateInfo());

            return result;
        }catch(LikeException l){
            //构造返回变量
            execution = new CropExecution(CropStateEnum.FOUND_NULL);
            result = new Result(execution.getState(),pageHelperResult,execution.getStateInfo());
            return result;
        }

    }

    public PlantInfo foundById(Integer plantid) {
        PlantInfo plantInfo = new PlantInfo();
        Plant plant;
        MatchImage matchImage;
        List<Image> list;
        Biologycategory biologycategory;
        try{
            plant = plantMapper.selectByKey(plantid);
            if(plant == null){
                throw new NotFoundException("没有找到该植物");
            }
            //数据库三个字段同时为主键，所以更改方法
            Example example = new Example(MatchImage.class);
            example.createCriteria().andEqualTo("matchid",plant.getPlantimage());
            List<MatchImage> matchImages = matchImageMapepr.selectByExample(example);
            matchImage = matchImages.get(0);


            list = imageMapper.selectByKey(matchImage.getImageid());
            biologycategory = categoryMapper.selectByKey(plant.getCategory());

            biologycategory.setBioOrder(categoryMapper.selectOrder(plant.getCategory()));
            biologycategory.set_class(categoryMapper.selectClass(plant.getCategory()));


            plantInfo.setPlant(plant);
            plantInfo.setBiologycategory(biologycategory);
            plantInfo.setList(list);


            return plantInfo;
        }catch (NotFoundException n){
            throw new NotFoundException("Infomation Error: "+ n.getMessage());
        }

    }
//级联查询
    public Category foundByCate(String kingdom, String phylum, String _class, String bio_order,
                                Integer pageNum, Integer pageSize) {
        //1.建立所需变量
        Category category = new Category();//具体的植物信息，以及总的植物条数
        List<Plant> list;
        Integer num;
        try{
            if(phylum.equals("null")){
                num = plantMapper.selectByCate4(kingdom);
            }else if(_class.equals("null")){
                num = plantMapper.selectByCate3(kingdom,phylum);
            }else if(bio_order.equals("null")){
                num = plantMapper.selectByCate2(kingdom,phylum,_class);
            }else {
                //2.获得所有的值
                num = plantMapper.selectByCate1(kingdom,phylum,_class,bio_order);
            }
            if(num == 0){
                throw new CategoryException("num为空");
            }
            //3.分页助手设置数据显示的页数和每页显示条数
            PageHelper.startPage(pageNum,pageSize);
            //查询数据库
            if(phylum.equals("null")){
                list = plantMapper.selectByCategory4(kingdom);
            }else if(_class.equals("null")){
                list = plantMapper.selectByCategory3(kingdom,phylum);
            }else if(bio_order.equals("null")){
                list = plantMapper.selectByCategory2(kingdom,phylum,_class);
            }else {
                //2.获得所有的值
                list = plantMapper.selectByCategory1(kingdom,phylum,_class,bio_order);
            }

            category.setList(list);
            category.setNum(num);

            //4.查询plant表并封装对象
            return category;
        }catch (CategoryException c){
            throw new CategoryException("Regist inner error:" + c.getMessage());
        }

    }


    public Result<Category> foundByPyName(String pyName, Integer pageNum, Integer pageSize) {
        Category category = new Category();
        List<Plant> list;
        CropExecution execution;
        Result<Category> result;
        try{
            list = plantMapper.likeSelect(pyName);
            Integer num = list.size();
            //设置分页助手
            PageHelper.startPage(pageNum,pageSize);
            category.setList(plantMapper.likeSelect(pyName));
            category.setNum(num);
            if(list == null){
                throw new NotFoundException("未找到任何相关信息，请重新上传图片");
            }
            execution = new CropExecution(CropStateEnum.SUCCESS);
            result = new Result(execution.getState(),category,execution.getStateInfo());
            return result;
        }catch (NotFoundException n){
            throw new CategoryException("foundByPyName inner error:" + n.getMessage());
        }
    }
}
