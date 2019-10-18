package cn.farm.Controller;

import cn.farm.dto.*;
import cn.farm.enums.CategoryStateEnum;
import cn.farm.enums.CropStateEnum;
import cn.farm.exception.CategoryException;
import cn.farm.exception.DefaultException;
import cn.farm.service.CategoryService;
import cn.farm.service.PlantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@EnableAutoConfiguration
@RequestMapping("/farm/common")
public class plantController {
    @Autowired
    private PlantService plantService;
    @Autowired
    private CategoryService categoryService;
    //模糊搜索crop
    //只返回chinesename，plantID，norphology三个属性
    @RequestMapping(value = "/plant/foundbyname",produces = "application/json")
    public Result cropFound(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "pageNum") Integer PageNum,
            @RequestParam(value = "pageSize") Integer PageSize
    )throws Exception {
        Result<PageHelperResult> result;//返回信息
        CropExecution execution;//返回Data
        try {
            result = plantService.foundByName(name, PageNum, PageSize);//获取只含有这三个属性的plant对象的集合
            return result;
        } catch (Exception e) {
            execution = new CropExecution(CropStateEnum.DEFAULT);
            result = new Result(execution.getState(), execution.getStateInfo());
            return result;
        }
    }

    //根据模糊搜索得到的数据再通过id查询具体数据
    @RequestMapping(value = "/plant/info",produces = "application/json")
    public Result<PlantInfo> plantInfo(
            @RequestParam(value = "plantid") Integer plantid
    ) throws Exception{
        Result<PlantInfo> result;
        CropExecution execution;
        PlantInfo plantInfo;

        plantInfo = plantService.foundById(plantid);
        execution = new CropExecution(CropStateEnum.SUCCESS);
        result = new Result(execution.getState(), plantInfo, execution.getStateInfo());
        return result;
    }

    //根据作物分类查询说有值
    @RequestMapping(value = "/plant/foundbycate",produces = "application/json")
    public Result<Category> foundbycate(
            @RequestParam(value = "kingdom") String kingdom,//界
            @RequestParam(value = "phylum") String phylum,//门
            @RequestParam(value = "_class") String _class,//纲
            @RequestParam(value = "bio_order") String bio_order,//目
            @RequestParam(value = "pageNum") Integer pageNum,
            @RequestParam(value = "pageSize") Integer pageSize
    )throws Exception {
        Category category;
        Result<Category> result;
        CategoryExecution execution;


        try {
            //获取数据
            category = plantService.foundByCate(kingdom, phylum, _class, bio_order, pageNum, pageSize);
            execution = new CategoryExecution(CategoryStateEnum.SUCCESS);
            result = new Result<>(execution.getState(), category, execution.getStateInfo());
            return result;
        } catch (CategoryException c) {
            execution = new CategoryExecution(CategoryStateEnum.DEFAULT);
            result = new Result<>(execution.getState(), execution.getStateInfo());
            return result;
        }
    }
    //====================级联查询界门纲目的所属关系=========================
    @RequestMapping(value = "/category/found")
    public Result FoundCategory(
            @RequestParam(value = "type")String type,
            @RequestParam(value = "value")String value
    ){
        Result<String> result;
        CategoryExecution execution;
        try {
            List<String> list = categoryService.foundByType(type,value);//获取只含有这三个属性的plant对象的集合
            execution = new CategoryExecution(CategoryStateEnum.SUCCESS);
            result = new Result(execution.getState(),list,execution.getStateInfo());
            return result;
        }catch (DefaultException d){
            execution = new CategoryExecution(CategoryStateEnum.DEFAULT);
            result = new Result(execution.getState(),execution.getStateInfo());
            return result;
        }catch (Exception e){
            execution = new CategoryExecution(CategoryStateEnum.INNER_ERROR);
            result = new Result(execution.getState(),execution.getStateInfo());
            return result;
        }
    }
}
