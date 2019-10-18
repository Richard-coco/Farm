package cn.farm.expert.controller;
import cn.farm.expert.config.FilePathConfig;
import cn.farm.expert.domain.Data;
import cn.farm.expert.domain.Model;
import cn.farm.expert.service.ModelService;
import cn.farm.expert.utils.MyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("farm/expert/model")
public class ModelController {

    @Autowired
    private ModelService modelService;

    @Autowired
    private FilePathConfig filePathConfig;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 查询录入的诊断模型
     * @param pageSize
     * @param pageNum
     * @return
     */
    @GetMapping(value = "find",produces = "application/json")
    public Data findByAuthor(@RequestParam(value = "userid",required = true) int author,
                                 @RequestParam(value = "pagesize",defaultValue = "3") int  pageSize,
                                @RequestParam(value = "pagenum",defaultValue = "1")int pageNum){
            //打印日志
            logger.info("userId:"+author+" 进行了查询");
            List<Model> modelList = modelService.findByAuthor(author);
            Data data = MyUtils.findTo(modelList,logger,pageSize,pageNum);
            return data;
    }

    /**
     * 添加诊断模型
     * @param author
     * @param file
     * @param modelName
     * @param description
     * @return
     */
    @PostMapping(value = "save",produces = "application/json")
    public Data add(@RequestParam(value = "userid",required = true) int author,
                    @RequestParam(value = "modelfile",required = true) MultipartFile file,
                    @RequestParam(value = "modelname",required = false)String modelName,
                    @RequestParam(value = "description",required = false)String description) {
            String modelPath = MyUtils.uploadFile(file,filePathConfig.getModelpath());
            //打印日志
            logger.info("userId:"+author+" 进行了添加操作");
            Model model = new Model();
            model.setAuthor(author);
            //存储文件路径
            model.setModelFile(modelPath);
            model.setDescription(description);
            model.setModelName(modelName);
            int sum = modelService.save(model);
            //打印日志
            Data data = MyUtils.saveTo(logger,sum);
            return data;
    }

    /**
     * 修改诊断模型 不能更新model文件
     * @param author
     * @param file
     * @param modelId
     * @param modelName
     * @param description
     * @return
     */
    @PostMapping(value = "update",produces = "application/json")
    public Data update(@RequestParam(value = "userid",required = true) int author,
                       @RequestParam(value = "modelfile`",required = false) MultipartFile file,
                       @RequestParam(value = "modelid",required = true)int modelId,
                       @RequestParam(value = "modelname",required = false)String modelName,
                       @RequestParam(value = "description",required = false)String description) throws IOException {

            //打印日志
            logger.info("userId:"+author+" 进行了更新操作");
            String path = MyUtils.uploadFile3(file,filePathConfig);
            Model model = new Model();
            model.setModelFile(path);
            model.setAuthor(author);
            model.setModelId(modelId);
            model.setDescription(description);
            model.setModelName(modelName);
            model.setAuthor(author);
            int sum = modelService.update(model);
            //打印日志
            Data data = MyUtils.updateTo(logger,sum);
            return data;

    }
    /**
     * 删除诊断模型
     * @param
     * @return
     */
    @GetMapping(value = "remove",produces = "application/json")
    public Data remove(@RequestParam(value = "userid",required = true) int author,
                       @RequestParam(value = "modelid",required = true) int modelId) {

        //打印日志
            logger.info("userId:"+author+" 进行了删除操作");
            int sum = modelService.remove(author,modelId);
            Data data = MyUtils.removeTo(sum,logger);
            return data;
}

}
