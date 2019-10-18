package cn.farm.service;

import cn.farm.dto.DiseaseExecution;
import cn.farm.dto.PageHelperResult;
import cn.farm.dto.Result;
import cn.farm.enums.DiseaseStateEnum;
import cn.farm.exception.LikeException;
import cn.farm.mapper.DiseaseMapper;
import cn.farm.pojo.Diseasestress;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DiseaseService {
    @Autowired
    private DiseaseMapper diseaseMapper;


    public Result<PageHelperResult> foundByName(String name, Integer pageNum, Integer pageSize) {
        //声明数据变量
        Result<PageHelperResult> result;//返回信息
        DiseaseExecution execution;//返回Data
        PageHelperResult<Diseasestress> pageHelperResult = new PageHelperResult<>();
        try{
            //查询所有数据
            List<Diseasestress> list = diseaseMapper.likeSelect(name);
            Integer all = list.size();

            //查询数据库
            PageHelper.startPage(pageNum,pageSize);//从第几条数据开始，每页显示多少条数据
            list = diseaseMapper.likeSelect(name);
            pageHelperResult.setNumber(all);
            pageHelperResult.setList(list);
            if(list.size() == 0){
                throw new LikeException("查询不到任何信息");
            }

            //构造返回变量
            execution = new DiseaseExecution(DiseaseStateEnum.SUCCESS);
            result = new Result(execution.getState(),pageHelperResult,execution.getStateInfo());

            return result;
        }catch(LikeException l){
            execution = new DiseaseExecution(DiseaseStateEnum.FOUND_NULL);
            result = new Result(execution.getState(),pageHelperResult,execution.getStateInfo());

            return result;
        }
    }
}
