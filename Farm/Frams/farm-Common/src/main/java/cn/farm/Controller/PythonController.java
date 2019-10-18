package cn.farm.Controller;

import cn.farm.dto.*;
import cn.farm.enums.CropStateEnum;
import cn.farm.enums.EnemyStateEnum;
import cn.farm.exception.LikeException;
import cn.farm.exception.NoPhotoException;
import cn.farm.exception.NotFoundException;
import cn.farm.exception.PythonException;
import cn.farm.service.EnemyService;
import cn.farm.service.PlantService;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@EnableAutoConfiguration
@RequestMapping("/farm/common")
public class PythonController {

    @Autowired
    private PlantService plantService;
    @Autowired
    private EnemyService enemyService;
    @Autowired
    private DownLoadPhoto downLoadPhoto;

    private String url= "http://localhost:9999/python/foundplant";
    //===============================刘大爷的分割线==========================================
    //调用python模块通过给定图片判定pliant
    @RequestMapping(value = "/plant/foundbyphoto")
    public Result<PageHelperResult> foundPlantByPhoto(
            @RequestParam(value = "photo") MultipartFile photo,//pageNum,pageSize
            @RequestParam(value = "pageNum")Integer pageNum,
            @RequestParam(value = "pageSize")Integer pageSize
    ) throws IOException {
        Result<PageHelperResult> result;
        CropExecution execution;
        try {
            //==================存图片====================
            String photoPath = downLoadPhoto.DownLoadPhoto(photo);//图片的绝对路径
            if(photoPath.equals("")){
                throw new NoPhotoException("没有上传照片");
            }
            //====================结束====================

            HttpClient client=new HttpClient();
            //=====================可能会出项问题
            PostMethod postMethod=new PostMethod(url);
            //3.设置请求参数
            //传图片
            FilePart filePart = new FilePart("file", new File(photoPath));//文件参数
            //修改请求的头部
            postMethod.setRequestHeader("Content-Type", "image/jpeg,image/gif,image/png; charset=utf-8");
            //4.执行请求 ,结果码
            int code;//=client.executeMethod(postMethod);
            code = 200;
            if(code==404){
                execution = new CropExecution(CropStateEnum.INNER_ERROR);
                result = new Result<>(execution.getState(),execution.getStateInfo());
                return result;
            }
            System.out.println("结果码:"+code);
            //5. 获取结果
            String plantName =postMethod.getResponseBodyAsString();
            System.out.println("Post请求的结果："+plantName);
            plantName = "海棠";

            //通过plantName查询plant
            result = plantService.foundByName(plantName,pageNum,pageSize);
            return result;
        }catch (NoPhotoException n){
            execution = new CropExecution(CropStateEnum.PHOTO_NULL);
            result = new Result(execution.getState(),execution.getStateInfo());
            return result;
        }catch (LikeException l){
            execution = new CropExecution(CropStateEnum.FOUND_NULL);
            result = new Result(execution.getState(),execution.getStateInfo());
            return result;
        }catch (Exception e){
            execution = new CropExecution(CropStateEnum.INNER_ERROR);
            result = new Result(execution.getState(),execution.getStateInfo());
            return result;
        }
    }
    //========================================================================================

    @RequestMapping(value = "/plant/foundbyphotoname")
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








    //=========================调用python模块检索天敌===========================================
    //以下是连接python模块操作返回值给用户
    //查询天敌
    @RequestMapping(value = "/enemy/foundbyphoto")
    public Result<EnemyCategory> foundEnemyByPhoto(
            @RequestParam(value = "photo") MultipartFile photo,//pageNum,pageSize
            @RequestParam(value = "pageNum")Integer pageNum,
            @RequestParam(value = "pageSize")Integer pageSize
    ) throws IOException {

        //创建变量
        Result<EnemyCategory> result;
        EnemyExecution execution;

        try {
            //==================存图片====================
            String photoPath = downLoadPhoto.DownLoadPhoto(photo);//图片的绝对路径
            //====================结束====================

            HttpClient client=new HttpClient();
            PostMethod postMethod=new PostMethod("http://localhost:9999/python/foundplant");
            //3.设置请求参数
            //传图片
            FilePart filePart = new FilePart("file", new File(photoPath));//文件参数
            //修改请求的头部
            postMethod.setRequestHeader("Content-Type", "text/xml; charset=utf-8");
            //4.执行请求 ,结果码
            int code;//=client.executeMethod(postMethod);
            code=200;
            if(code!=200){
                execution = new EnemyExecution(EnemyStateEnum.PYTHON_ERROR);
                result = new Result<>(execution.getState(),execution.getStateInfo());
                return result;
            }
            System.out.println("结果码:"+code);
            //5. 获取结果
            String enemyName =postMethod.getResponseBodyAsString();
            System.out.println("Post请求的结果："+enemyName);
            enemyName = "虱";

            //=================================

            result = enemyService.foundByPyName(enemyName,pageNum,pageSize);
            return result;//服务器中的图片地址

        }catch (NoPhotoException n){
            execution = new EnemyExecution(EnemyStateEnum.PHOTO_NULL);
            result = new Result(execution.getState(),execution.getStateInfo());
            return result;//图片为空
        }catch (PythonException p){
            execution = new EnemyExecution(EnemyStateEnum.PYTHON_ERROR);
            result = new Result(execution.getState(),execution.getStateInfo());
            return result;//Python模块出错
        }catch (NotFoundException n){
            execution = new EnemyExecution(EnemyStateEnum.FOUND_NULL);
            result = new Result(execution.getState(),execution.getStateInfo());
            return result;//数据访问未找到相关信息
        }catch (Exception e){
            execution = new EnemyExecution(EnemyStateEnum.INNER_ERROR);
            result = new Result(execution.getState(),execution.getStateInfo());
            return result;//系统异常
        }

    }

    @RequestMapping(value = "/enemy/foundbyphotoname")
    public Result enemyFound(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "pageNum") Integer PageNum,
            @RequestParam(value = "pageSize") Integer PageSize
    )throws Exception {
        Result<PageHelperResult> result;//返回信息
        EnemyExecution execution;//返回Data
        try {
            result = enemyService.foundByName(name, PageNum, PageSize);//获取只含有这三个属性的plant对象的集合
            return result;
        } catch (Exception e) {
            execution = new EnemyExecution(EnemyStateEnum.DEFAULT);
            result = new Result(execution.getState(), execution.getStateInfo());
            return result;
        }
    }

}
