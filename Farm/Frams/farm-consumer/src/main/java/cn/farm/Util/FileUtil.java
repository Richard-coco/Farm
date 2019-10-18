package cn.farm.Util;


import org.springframework.core.io.FileSystemResource;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class FileUtil {

    static String tempPath = "C:\\Users\\Administrator\\Desktop\\temp\\";

    //排除List中为空的元素
    public static List<MultipartFile> getNotNullList(List<MultipartFile> list){
        Iterator<MultipartFile> iterator = list.iterator();
        List<MultipartFile> NotNullList = new ArrayList<>();
        MultipartFile multipartFile =null;
        while(iterator.hasNext()){
            multipartFile = iterator.next();
            if(multipartFile != null){
                NotNullList.add(multipartFile);
            }
        }
        return NotNullList;
    }

    //下载文件
    public static void downloadList(List<MultipartFile> multipartFileList, String path, String endWith) throws IOException {
        InputStream in = null;
        OutputStream out = null;
        byte[] b = new byte[1024*8];
        int i = -1;
        int num = 1;
        Iterator<MultipartFile> iterator = multipartFileList.iterator();
        while(iterator.hasNext()){
            in = iterator.next().getInputStream();
            out = new FileOutputStream(path+"_"+num+"."+endWith);
            while((i = in.read(b)) != -1){
                out.write(b);
            }
        }
        in.close();
        out.close();
    }

    //下载文件
    public static void download(MultipartFile multipartFile, String path) throws IOException {
        InputStream in = multipartFile.getInputStream();
        OutputStream out = new FileOutputStream(path);
        byte[] b = new byte[1024*8];
        int i = -1;
        int num = 1;
        while((i = in.read(b))!=-1){
            out.write(b);
        }
        in.close();
        out.close();
    }

    //MultipartFile转化FileSystemResource
    public static FileSystemResource multipartFileToFileSystemResource(MultipartFile multipartFile) throws IOException {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd-HH-mm-ss");
        String time = simpleDateFormat.format(date);
        String path = tempPath+time+""+multipartFile.toString();

        FileUtil.download(multipartFile,path);
        FileSystemResource resource = new FileSystemResource(new File(path));
        return resource;
    }


    //将file1 2 3 转化FileSystemResource 装入 param
    public static void addFile(MultipartFile file1 , MultipartFile file2 , MultipartFile file3 , MultiValueMap<String,Object> param ){

        try {

            if(file1 != null){
                param.add("file1", multipartFileToFileSystemResource(file1));
            }
            if(file2 != null){
                param.add("file2", multipartFileToFileSystemResource(file2));
            }
            if(file3 != null){
                param.add("file3", multipartFileToFileSystemResource(file3));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
