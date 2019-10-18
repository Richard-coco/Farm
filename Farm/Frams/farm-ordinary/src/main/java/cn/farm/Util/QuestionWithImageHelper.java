package cn.farm.Util;


import cn.farm.Entity.QuestionWithImage;
import cn.farm.Service.ImageService;
import cn.farm.Service.MatchImageService;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class QuestionWithImageHelper {

    //将咨询中的image对应的ImageID封装到map中
    public static List<QuestionWithImage> addImageID(List<QuestionWithImage> questionWithImages ,
                                                     MatchImageService matchImageService, ImageService imageService){
        Iterator<QuestionWithImage> iterator = questionWithImages.iterator();
        List<Integer> images;
        Map<String,String> map;
        QuestionWithImage questionWithImage;
        while(iterator.hasNext()){
            questionWithImage = iterator.next();
            images = matchImageService.selectByMatchID(questionWithImage.getImage());
            if(images != null){
                map = imageService.selectImageFileByImageID(images);
                questionWithImage.setMap(map);
            }
        }
        return questionWithImages;
    }

    //将咨询中的image对应的ImageID封装到map中
//    public static List<QuestionWithImage> addImageID(List<QuestionWithImage> questionWithImages ,
//                                                     MatchImageService matchImageService, ImageService imageService){
//        Iterator<QuestionWithImage> iterator = questionWithImages.iterator();
//        List<Integer> images;
//        Map<String,String> map ;
//        QuestionWithImage questionWithImage;
//        List list ;
//        while(iterator.hasNext()){
//            questionWithImage = iterator.next();
//            images = matchImageService.selectByMatchID(questionWithImage.getImage());
//            if(images != null){
//                list = imageService.selectImageFileByImageID(images);
//                questionWithImage.setList(list);
//            }
//        }
//        return questionWithImages;
//    }
}
