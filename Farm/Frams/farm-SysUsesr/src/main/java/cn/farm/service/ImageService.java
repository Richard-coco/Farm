package cn.farm.service;


import cn.farm.entity.Image;

import java.util.List;


public interface ImageService {

    List<Integer> addImageList(List<Image> images);
    int addImage(Image image);
    void deleteImage(Integer imageid);
    void updateImage(Image image);
    void findImage(Integer imageid);
}
