package cn.farm.Controller;

import cn.farm.dto.*;
import cn.farm.enums.*;
import cn.farm.exception.*;
import cn.farm.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
@RequestMapping("/farm/common")
public class protectController {
    @Autowired
    private ProtectService protectService;
    @Autowired
    private MessageService messageService;

    //==========植保（标题模糊搜索）搜索===========
    @RequestMapping(value = "/protect/foundbykey",produces = "application/json")
    public Result FoundByProtect(
            @RequestParam(value = "keyword")String keyword,
            @RequestParam(value = "PageNum")Integer PageNum,
            @RequestParam(value = "PageSize")Integer PageSize
    )throws Exception{
        Result<PageHelperResult> result;//返回信息
        ProtectExecution execution;//返回Data
        try {
            result = protectService.foundByName(keyword, PageNum, PageSize);//获取只含有这三个属性的plant对象的集合
            return result;
        }catch (LikeException l){
            execution = new ProtectExecution(ProtectStateEnum.FOUND_NULL);
            result = new Result(execution.getState(), execution.getStateInfo());
            return result;
        } catch (Exception e) {
            execution = new ProtectExecution(ProtectStateEnum.INNER_ERROR);
            result = new Result(execution.getState(), execution.getStateInfo());
            return result;
        }
    }
   //咨询信息模糊查询
    @RequestMapping(value = "/message/foundbykey",produces = "application/json")
    public Result Messagefoundbytitle(
            @RequestParam(value = "keyword")String keyword,
            @RequestParam(value = "PageNum")Integer PageNum,
            @RequestParam(value = "PageSize")Integer PageSize
    )throws Exception{
        Result<PageHelperResult> result;//返回信息
        MessageExecution execution;//返回Data
        try {
            result = messageService.foundByName(keyword, PageNum, PageSize);//获取只含有这三个属性的plant对象的集合
            return result;
        }catch (LikeException l){
            execution = new MessageExecution(MessageStateEnum.FOUND_NULL);
            result = new Result(execution.getState(), execution.getStateInfo());
            return result;
        } catch (Exception e) {
            execution = new MessageExecution(MessageStateEnum.DEFAULT);
            result = new Result(execution.getState(), execution.getStateInfo());
            return result;
        }
    }


}
