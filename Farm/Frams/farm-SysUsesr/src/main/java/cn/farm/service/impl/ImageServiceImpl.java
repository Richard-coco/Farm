package cn.farm.service.impl;

import cn.farm.entity.Image;
import cn.farm.mapper.ImageMapperSql;
import cn.farm.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
;import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    private ImageMapperSql imageMapperSql;

    @Transactional
    //插入图片List 返回图片ImageIDList 如果images为空返回null
    @Override
    public List<Integer> addImageList(List<Image> images) {
        if(images.size() == 0){
            return null;
        }
       List<Integer> list = new ArrayList<>();
       Iterator<Image> iterator = images.iterator();
       int imageid;
       Image image;
       while(iterator.hasNext()){
           image = iterator.next();
           imageMapperSql.insert(image);
           imageid = image.getImageid();
           list.add(imageid);
       }
       return list;
    }

    @Transactional
    @Override
    public int addImage(Image image) {
        imageMapperSql.insert(image);
        return image.getImageid();
    }

    @Override
    public void deleteImage(Integer imageid) {
        imageMapperSql.delete(imageid);
    }

    @Override
    public void updateImage(Image image) {
        imageMapperSql.update(image);
    }

    @Override
    public void findImage(Integer imageid) {
        imageMapperSql.select(imageid);
    }
}
