package cn.farm.Controller;

import cn.farm.dto.*;
import cn.farm.enums.EnemyStateEnum;
import cn.farm.exception.CategoryException;
import cn.farm.exception.LikeException;
import cn.farm.exception.NotFoundException;
import cn.farm.service.EnemyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
@RequestMapping("/farm/common")
public class enemyController {

    @Autowired
    private EnemyService enemyService;
    //模糊搜索天敌名称
    @RequestMapping(value = "/enemy/foundbyname",produces = "application/json")
    public Result EnemyFound(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "pageNum") Integer PageNum,
            @RequestParam(value = "pageSize") Integer PageSize
    ) throws Exception{
        Result<PageHelperResult> result;//返回信息
        EnemyExecution execution;//返回Data
        try {
            result = enemyService.foundByName(name, PageNum, PageSize);//获取只含有这三个属性的plant对象的集合
            return result;
        } catch (LikeException l){
            execution = new EnemyExecution(EnemyStateEnum.FOUND_NULL);
            result = new Result(execution.getState(),execution.getStateInfo());
            return result;
        }catch (Exception e) {
            execution = new EnemyExecution(EnemyStateEnum.DEFAULT);
            result = new Result(execution.getState(),execution.getStateInfo());
            return result;
        }
    }
//========================显示enemy具体信息=====================

    //根据模糊搜索得到的数据再通过id查询具体数据
    @RequestMapping(value = "/enemy/info",produces = "application/json")
    public Result<EnemyInfo> EnemyInfo(
            @RequestParam(value = "enemyid") Integer enemyid
    ) throws Exception{
        Result<EnemyInfo> result;
        EnemyExecution execution;
        EnemyInfo enemyInfo;

        try {
            enemyInfo = enemyService.foundById(enemyid);
            execution = new EnemyExecution(EnemyStateEnum.SUCCESS);
            result = new Result(execution.getState(), enemyInfo, execution.getStateInfo());
            return result;
        }catch (NotFoundException n){
            execution = new EnemyExecution(EnemyStateEnum.FOUND_NULL);
            result = new Result(execution.getState(), execution.getStateInfo());
            return result;
        }catch (Exception e){
            execution = new EnemyExecution(EnemyStateEnum.INNER_ERROR);
            result = new Result(execution.getState(), execution.getStateInfo());
            return result;
        }

    }
    //联动检索天敌
    @RequestMapping(value = "/enemy/foundbycate",produces = "application/json")
    public Result<EnemyCategory> foundbyEnemycate(
            @RequestParam(value = "kingdom") String kingdom,//界
            @RequestParam(value = "phylum") String phylum,//门
            @RequestParam(value = "_class") String _class,//纲
            @RequestParam(value = "bio_order") String bio_order,//目
            @RequestParam(value = "pageNum") Integer pageNum,
            @RequestParam(value = "pageSize") Integer pageSize
    ) throws Exception{
        EnemyCategory category;
        Result<EnemyCategory> result;
        EnemyExecution execution;


        try {
            //获取数据
            category = enemyService.foundByCate(kingdom, phylum, _class, bio_order, pageNum, pageSize);
            execution = new EnemyExecution(EnemyStateEnum.SUCCESS);
            result = new Result<>(execution.getState(), category, execution.getStateInfo());
            return result;
        } catch (CategoryException c) {
            execution = new EnemyExecution(EnemyStateEnum.FOUND_NULL);
            result = new Result<>(execution.getState(), execution.getStateInfo());
            return result;
        }catch (Exception e){
            execution = new EnemyExecution(EnemyStateEnum.INNER_ERROR);
            result = new Result<>(execution.getState(), execution.getStateInfo());
            return result;
        }
    }
}
