package cn.farm.Controller;

import cn.farm.dto.DiseaseExecution;
import cn.farm.dto.PageHelperResult;
import cn.farm.dto.PestExecution;
import cn.farm.dto.Result;
import cn.farm.enums.DiseaseStateEnum;
import cn.farm.enums.PestStateEnum;
import cn.farm.exception.NotFoundException;
import cn.farm.pojo.Peststress;
import cn.farm.service.DiseaseService;
import cn.farm.service.PestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
@RequestMapping("/farm/common")
public class dengerController {
    @Autowired
    private DiseaseService diseaseService;
    @Autowired
    private PestService pestService;
    //病害名称模糊查询
    @RequestMapping(value = "/disease/foundbyname",produces = "application/json")
    public Result<PageHelperResult> diseaseFound(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "pageNum") Integer PageNum,
            @RequestParam(value = "pageSize") Integer PageSize
    ) throws Exception{
        Result<PageHelperResult> result;//返回信息
        DiseaseExecution execution;//返回Data
        try {
            result = diseaseService.foundByName(name, PageNum, PageSize);//获取只含有这三个属性的plant对象的集合
            return result;
        } catch (Exception e) {
            execution = new DiseaseExecution(DiseaseStateEnum.DEFAULT);
            result = new Result(execution.getState(), execution.getStateInfo());
            return result;
        }

    }

    //虫害名称模糊检索
    @RequestMapping(value = "/pest/foundbyname",produces = "application/json")
    public Result<PageHelperResult> pestFound(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "pageNum") Integer PageNum,
            @RequestParam(value = "pageSize") Integer PageSize
    )throws Exception {
        Result<PageHelperResult> result;//返回信息
        PestExecution execution;//返回Data
        try {
            result = pestService.foundByName(name, PageNum, PageSize);//获取只含有这三个属性的plant对象的集合
            return result;
        }catch (Exception e) {
            execution = new PestExecution(PestStateEnum.DEFAULT);
            result = new Result(execution.getState(), execution.getStateInfo());
            return result;
        }

    }

    //虫害联动查询
    @RequestMapping(value = "/pest/foundbycate",produces = "application/json")
    public Result foundpestbycate(
            @RequestParam(value = "kingdom") String kingdom,//界
            @RequestParam(value = "phylum") String phylum,//门
            @RequestParam(value = "_class") String _class,//纲
            @RequestParam(value = "bio_order") String bio_order,//目
            @RequestParam(value = "pageNum") Integer pageNum,
            @RequestParam(value = "pageSize") Integer pageSize
    ) throws Exception{
        PageHelperResult<Peststress> pests;
        Result<PageHelperResult> result;
        PestExecution execution;


        try {
            //获取数据
            pests = pestService.foundByCate(kingdom, phylum, _class, bio_order, pageNum, pageSize);
            execution = new PestExecution(PestStateEnum.SUCCESS);
            result = new Result(execution.getState(), pests, execution.getStateInfo());
            return result;
        } catch (NotFoundException c) {
            execution = new PestExecution(PestStateEnum.FOUND_NULL);
            result = new Result<>(execution.getState(), execution.getStateInfo());
            return result;
        }catch (Exception e){
            execution = new PestExecution(PestStateEnum.INNER_ERROR);
            result = new Result<>(execution.getState(), execution.getStateInfo());
            return result;
        }
    }

}
