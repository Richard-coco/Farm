package cn.farm.service;

import cn.farm.dto.PageHelperResult;
import cn.farm.dto.PestExecution;
import cn.farm.dto.Result;
import cn.farm.enums.PestStateEnum;
import cn.farm.exception.CategoryException;
import cn.farm.exception.LikeException;
import cn.farm.exception.NotFoundException;
import cn.farm.mapper.PestMapper;
import cn.farm.pojo.Peststress;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PestService {
    @Autowired
    private PestMapper pestMapper;

    public Result<PageHelperResult> foundByName(String name, Integer pageNum, Integer pageSize) {
        //声明数据变量
        Result<PageHelperResult> result;//返回信息
        PestExecution execution;//返回Data
        PageHelperResult<Peststress> pageHelperResult = new PageHelperResult<>();
        try{
            //查询所有数据
            List<Peststress> list = pestMapper.likeSelect(name);
            Integer all = list.size();

            //查询数据库
            PageHelper.startPage(pageNum,pageSize);//从第几条数据开始，每页显示多少条数据
            list = pestMapper.likeSelect(name);
            pageHelperResult.setList(list);
            pageHelperResult.setNumber(all);
            if(list.size() == 0){
                throw new LikeException("查询不到任何信息");
            }

            //构造返回变量
            execution = new PestExecution(PestStateEnum.SUCCESS);
            result = new Result(execution.getState(),pageHelperResult,execution.getStateInfo());

            return result;
        }catch(LikeException l){
            execution = new PestExecution(PestStateEnum.FOUND_NULL);
            result = new Result(execution.getState(),pageHelperResult,execution.getStateInfo());

            return result;
        }
    }

    public PageHelperResult foundByCate(String kingdom, String phylum, String _class, String bio_order, Integer pageNum, Integer pageSize) {
        //1.建立所需变量
        PageHelperResult<Peststress> pests = new PageHelperResult();//具体的植物信息，以及总的植物条数
        List<Peststress> list;
        Integer num;
        try{
            if(phylum.equals("null")){
                num = pestMapper.selectByCate4(kingdom);
            }else if(_class.equals("null")){
                num = pestMapper.selectByCate3(kingdom,phylum);
            }else if(bio_order.equals("null")){
                num = pestMapper.selectByCate2(kingdom,phylum,_class);
            }else {
                //2.获得所有的值
                num = pestMapper.selectByCate1(kingdom,phylum,_class,bio_order);
            }
            if(num == 0){
                throw new NotFoundException("num为空");
            }
            //3.分页助手设置数据显示的页数和每页显示条数
            PageHelper.startPage(pageNum,pageSize);
            //查询数据库
            if(phylum.equals("null")){
                list = pestMapper.selectByCategory4(kingdom);
            }else if(_class.equals("null")){
                list = pestMapper.selectByCategory3(kingdom,phylum);
            }else if(bio_order.equals("null")){
                list = pestMapper.selectByCategory2(kingdom,phylum,_class);
            }else {
                //2.获得所有的值
                list = pestMapper.selectByCategory1(kingdom,phylum,_class,bio_order);
            }
            pests.setList(list);
            pests.setNumber(num);

            //4.查询plant表并封装对象
            return pests;
        }catch (NotFoundException n){
            throw new  NotFoundException("NotFound0sd inner error:" + n.getMessage());
        }
    }
}
