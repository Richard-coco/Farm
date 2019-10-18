package cn.farm.Util;


import cn.farm.Util.Determiner.Determiner;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FileUtil {

    String path;

    //通过判断器判断文件
    public static boolean doFilter(List<MultipartFile> multipartFileList, Determiner determiner) throws IOException {
        Iterator<MultipartFile> iterator = multipartFileList.iterator();
        while(iterator.hasNext()){
            determiner.setFile(iterator.next());
            if(determiner.determine() == false){
                return false;
            }
        }
        return true;
    }

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


}
