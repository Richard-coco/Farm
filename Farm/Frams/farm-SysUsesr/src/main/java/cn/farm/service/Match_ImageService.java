package cn.farm.service;

import cn.farm.entity.Image;
import cn.farm.entity.Match_Image;

import java.util.List;

public interface Match_ImageService {
    int addListImage(List<Match_Image> match_images);

    void deleteImage(Integer matchid);

    void updateImage(Match_Image match_image);

    void findImage(Integer matchid);
}
