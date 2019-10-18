package cn.farm.expert.utils;

import cn.farm.expert.config.FilePathConfig;
import cn.farm.expert.domain.Data;
import cn.farm.expert.domain.JsonData1;
import cn.farm.expert.exception.ZmException;
import org.slf4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.util.List;
import java.util.UUID;

public class MyUtils {


    //传图接口v3.0  传图+修改文件名
    public static String uploadFile3(MultipartFile file, FilePathConfig filePathConfig) throws ZmException,IOException {

       if (null != file) {

           String uuid = UUID.randomUUID().toString().replaceAll("-", "");
           String contentType = file.getContentType();   //图片文件类型
           String imageName = contentType.substring(contentType.indexOf("/") + 1);  // 获得文件后缀名称
           String oldFileName = file.getOriginalFilename();  //图片名字
           String newFileName = uuid + "." + imageName;    //新文件名称
           String filePath = filePathConfig.getImagepath();  //从配置文件中获取文件存放路径

           String oldFileUrl = filePath + oldFileName; //修改前文件路径
           String newFileUrl = filePath + newFileName; //修改后文件路径

           File file1 = new File(filePathConfig.getImagepath());
           if (!file1.exists()) {
               file1.mkdirs();
           }
           FileOutputStream out = new FileOutputStream(oldFileUrl);
           out.write(file.getBytes());
           out.flush();
           out.close();
           File oldFile = new File(oldFileUrl);
           oldFile.renameTo(new File(newFileUrl));  //修改传入的文件名称
           return newFileName;
       }else {
           return null;
       }
    }

    //生成随机八位数
    public static String uuid8(){
        String str = "";
        str += (int)(Math.random()*9+1);
        for(int i = 0; i < 7; i++){//更改这里 可以控制生成数字的位数
            str += (int)(Math.random()*10);
    }
        return str;
    }

    public static String uploadFile(MultipartFile file,String path){
        if (file.isEmpty()) {
            return "上传失败，请选择文件";
        }

        String fileName = file.getOriginalFilename();
        String filePath = path;
        File dest = new File(filePath + fileName);
        try {
            file.transferTo(dest);
            return filePath + fileName;
        } catch (IOException e) {

        }
        return "上传失败！";
    }

    public static Data removeTo(int sum, Logger logger){
       try {
        if (sum == 0){
            //打印日志
            logger.info("删除数据不存在");
            Data data = new Data();
            data.setCode(400);
            data.setMsg("invalid request");
            return data;
        }
        //打印日志
        logger.info("删除条数"+sum);
        Data data = new Data();
        JsonData1 jsonData1 = new JsonData1();
        jsonData1.setData(sum);
        data.setData(jsonData1);
        data.setCode(204);
        data.setMsg("no content");
        return data;
    }catch(Exception e){
        //打印日志
        logger.error("删除出错了");
        Data data = new Data();
        data.setCode(500);
        data.setMsg("server error");
        return data;
    }
    }

    public static Data findTo(List modelList,Logger logger,int pageSize,int pageNum){

       try {
        if (modelList.isEmpty()){
            //打印日志
            logger.info("查询结果为空");
            Data data = new Data();
            data.setCode(404);
            //查询结果为空
            data.setMsg("un404defined");
            return data;
        }else {
            //打印日志
            logger.info("查询成功"+"返回条数为"+modelList.size());

            Data data = new Data();
            JsonData1 jsonData1 = new JsonData1();
            jsonData1.setData(modelList);
            jsonData1.setPageSize(pageSize);
            jsonData1.setPageNum(pageNum);
            data.setData(jsonData1);
            data.setCode(200);
            data.setMsg("ok");
            return data;
        }
    }catch (Exception e){
        //打印日志
        logger.error("查询出错了");
        Data data = new Data();
        data.setCode( 500);
        data.setMsg("server error");
        return data;
    }
    }

    public static Data saveTo(Logger logger,int sum){
       try {
        logger.info("添加成功条数为"+sum);
        Data data = new Data();
        JsonData1 jsonData1 = new JsonData1();
        jsonData1.setData(sum);
        data.setData(jsonData1);
        data.setCode(200);
        data.setMsg("created");
        return data;
    }catch (Exception e){
        //打印日志
        logger.error("添加出错了");
        Data data = new Data();
        data.setCode(500);
        data.setMsg("server error");
        return data;
    }
    }

    public static Data updateTo(Logger logger,int sum){

       try {
        if (sum != 0){
        logger.info("更新条数："+sum);
        Data data = new Data();
        JsonData1 jsonData1 = new JsonData1();
        jsonData1.setData(sum);
        data.setData(jsonData1);
        data.setCode(200);
        data.setMsg("created");
        return data;
    } else {
        //打印日志
        logger.info("更新行不存在");
        Data data = new Data();
        data.setCode(400);
        //更新行不存在
        data.setMsg("invalid request");
        return data;
    }
}catch (Exception e){
        //打印日志
        logger.error("更新出错了");
        Data data = new Data();
        data.setCode(500);
        data.setMsg("server error");
        return data;
        }
    }
}

