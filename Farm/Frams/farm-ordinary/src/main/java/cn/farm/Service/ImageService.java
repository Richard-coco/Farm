package cn.farm.Service;

import cn.farm.Entity.Image;

import cn.farm.Mapper.ImageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.List;

@Service
@Transactional
public class ImageService {

    @Autowired
    ImageMapper imageMapper;

    public void insert(Image image){
        imageMapper.insert(image);
    }

    public Image selectByImageID(int imageID){
        return imageMapper.selectByImageID(imageID);
    }

    public List<Integer> insertImageList(List<Image> images){
        List<Integer> list = new ArrayList<>();
        Iterator<Image> iterator = images.iterator();
        Image image;
        int key;
        while(iterator.hasNext()){
            image = iterator.next();
            imageMapper.insert(image);
            key = image.getImageID();
            list.add(key);
        }
        return list;
    }

    //通过装有imageID的List集合 查找出imageFile 如果集合为空返回null
    public Map<String,String> selectImageFileByImageID(List<Integer> list){
        if(list.size() == 0 ){
            return null;
        }
        Map<String,String> map = new HashMap<>();
        Iterator<Integer> iterator = list.iterator();
        Integer imageID;
        String imageFile;
        while(iterator.hasNext()){
            imageID = iterator.next();
            imageFile = imageMapper.selectImageFileByImageID(imageID);
            //map.put("imageID:"+imageID,imageFile);
            map.put(imageID+"",imageFile);
        }
        return map;
    }


    //通过装有imageID的List集合 查找出imageFile 如果集合为空返回null
//    public List selectImageFileByImageID(List<Integer> list){
//        if(list.size() == 0 ){
//            return null;
//        }
//        List<String> imagesFilePath = new ArrayList<>();
//        Iterator<Integer> iterator = list.iterator();
//        Integer imageID;
//        String imageFile;
//        while(iterator.hasNext()){
//            imageID = iterator.next();
//            imageFile = imageMapper.selectImageFileByImageID(imageID);
//            imagesFilePath.add(imageID+"-"+imageFile);
//        }
//        return imagesFilePath;
//    }
}
