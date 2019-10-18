package cn.farm.service;

import cn.farm.dto.*;
import cn.farm.enums.EnemyStateEnum;
import cn.farm.exception.CategoryException;
import cn.farm.exception.LikeException;
import cn.farm.exception.NotFoundException;
import cn.farm.mapper.CategoryMapper;
import cn.farm.mapper.EnemyMapper;
import cn.farm.mapper.ImageMapper;
import cn.farm.mapper.MatchImageMapper;
import cn.farm.pojo.*;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
@Service
public class EnemyService {

    @Autowired
    private EnemyMapper enemyMapper;
    @Autowired
    private MatchImageMapper matchImageMapepr;
    @Autowired
    private ImageMapper imageMapper;
    @Autowired
    private CategoryMapper categoryMapper;

    public Result<PageHelperResult> foundByName(String name, Integer pageNum, Integer pageSize) {
        //声明数据变量
        Result<PageHelperResult> result;//返回信息
        EnemyExecution execution;//返回Data
        PageHelperResult<Enemy> pageHelperResult = new PageHelperResult<>();
        try{
            //查询所有数据
            List<Enemy> list = enemyMapper.likeSelect(name);
            Integer all = list.size();
            if(list.isEmpty()){
                throw new LikeException("查询不到任何信息");
            }
            //查询数据库
            PageHelper.startPage(pageNum,pageSize);//从第几条数据开始，每页显示多少条数据
            list = enemyMapper.likeSelect(name);
            pageHelperResult.setList(list);
            pageHelperResult.setNumber(all);
            pageHelperResult.setName(name);
            if(list == null){
                throw new LikeException("查询不到任何信息");
            }

            //构造返回变量
            execution = new EnemyExecution(EnemyStateEnum.SUCCESS);
            result = new Result(execution.getState(),pageHelperResult,execution.getStateInfo());
            return result;
        }catch(LikeException l){
            execution = new EnemyExecution(EnemyStateEnum.FOUND_NULL);
            result = new Result(execution.getState(),execution.getStateInfo());
            return result;
        }
    }

    public EnemyInfo foundById(Integer enemyid) {
        EnemyInfo enemyInfo = new EnemyInfo();
        Enemy enemy;
        MatchImage matchImage;
        List<Image> images;
        Biologycategory biologycategory;
        try{
            enemy = enemyMapper.selectByKey(enemyid);
            if(enemy == null){
                throw new NotFoundException("没有找到该天敌");
            }
            //数据库三个字段同时为主键，所以更改方法
            Example example = new Example(MatchImage.class);
            example.createCriteria().andEqualTo("matchid",enemy.getImageid());
            List<MatchImage> list = matchImageMapepr.selectByExample(example);
            matchImage = list.get(0);


            images = imageMapper.selectByKey(matchImage.getImageid());
            biologycategory = categoryMapper.selectByKey(enemy.getCategory());
            biologycategory.set_class(categoryMapper.selectClass(enemy.getCategory()));
            biologycategory.setBioOrder(categoryMapper.selectOrder(enemy.getCategory()));
            enemyInfo.setBiologycategory(biologycategory);
            enemyInfo.setList(images);
            enemyInfo.setEnemy(enemy);

            return enemyInfo;
        }catch (NotFoundException n){
            throw new NotFoundException("Infomation Error: "+ n.getMessage());
        }
    }

    public EnemyCategory foundByCate(String kingdom, String phylum, String _class, String bio_order, Integer pageNum, Integer pageSize) {
        //1.建立所需变量
        EnemyCategory category = new EnemyCategory();//具体的植物信息，以及总的植物条数
        List<Enemy> list;
        Integer num;
        try{
            if(phylum.equals("null")){
                num = enemyMapper.selectByCate4(kingdom);
            }else if(_class.equals("null")){
                num = enemyMapper.selectByCate3(kingdom,phylum);
            }else if(bio_order.equals("null")){
                num = enemyMapper.selectByCate2(kingdom,phylum,_class);
            }else {
                //2.获得所有的值
                num = enemyMapper.selectByCate1(kingdom,phylum,_class,bio_order);
            }
            if(num == 0){
                throw new CategoryException("num为空");
            }
            //3.分页助手设置数据显示的页数和每页显示条数
            PageHelper.startPage(pageNum,pageSize);
            //查询数据库

            if(phylum.equals("null")){
                list = enemyMapper.selectByCategory4(kingdom);
            }else if(_class.equals("null")){
                list = enemyMapper.selectByCategory3(kingdom,phylum);
            }else if(bio_order.equals("null")){
                list = enemyMapper.selectByCategory2(kingdom,phylum,_class);
            }else {
                //2.获得所有的值
                list = enemyMapper.selectByCategory1(kingdom,phylum,_class,bio_order);
            }

            category.setNum(num);
            category.setList(list);


            //4.查询plant表并封装对象
            return category;
        }catch (CategoryException c){
            throw new  CategoryException("Regist inner error:" + c.getMessage());
        }
    }

    public Result<EnemyCategory> foundByPyName(String pyName, Integer pageNum, Integer pageSize) {
        EnemyCategory category = new EnemyCategory();
        List<Enemy> list;
        EnemyExecution execution;
        Result<EnemyCategory> result;


        try{
            list = enemyMapper.likeSelect(pyName);
            Integer num = list.size();
            //设置分页助手
            PageHelper.startPage(pageNum,pageSize);
            category.setList(enemyMapper.likeSelect(pyName));
            category.setNum(num);
            if(list.size() == 0){
                throw new NotFoundException("未找到任何相关信息，请重新上传图片");
            }
            execution = new EnemyExecution(EnemyStateEnum.SUCCESS);
            result = new Result(execution.getState(),category,execution.getStateInfo());
            return result;
        }catch (NotFoundException n){
            throw new  NotFoundException("foundByPyName inner error:" + n.getMessage());
        }
    }
}
