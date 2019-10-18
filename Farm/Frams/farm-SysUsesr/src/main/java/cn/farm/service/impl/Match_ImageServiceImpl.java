package cn.farm.service.impl;

import cn.farm.entity.Match_Image;
import cn.farm.mapper.MatchImageMapperSql;
import cn.farm.service.Match_ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class Match_ImageServiceImpl implements Match_ImageService {

    @Autowired
    private MatchImageMapperSql matchImageMapperSql;

    @Transactional
    @Override
    public int addListImage(List<Match_Image> match_images ){
        if(match_images.size() ==0 || match_images == null){
            return 0;
        }
        Match_Image matchImage;
        matchImage = match_images.get(0);
        matchImageMapperSql.insert(matchImage);
        Integer matchid = matchImage.getMatchid();
        for (int i = 1 ; i < match_images.size() ; ++i){
            match_images.get(1).setMatchid(matchid);  //设置match_images.get(i)为同一个matchid。
            matchImageMapperSql.insertWithPrimaryKey(match_images.get(1));
        }
        return matchid;

    }

    @Override
    public void deleteImage(Integer matchid) {
        matchImageMapperSql.delete(matchid);
    }

    @Override
    public void updateImage(Match_Image match_image) {
        matchImageMapperSql.update(match_image);
    }

    @Override
    public void findImage(Integer matchid) {
        matchImageMapperSql.select(matchid);
    }

}
