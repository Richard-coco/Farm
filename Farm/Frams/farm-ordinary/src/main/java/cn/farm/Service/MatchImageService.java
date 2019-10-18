package cn.farm.Service;


import cn.farm.Entity.MatchImage;
import cn.farm.Mapper.MatchImageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchImageService {

    @Autowired
    MatchImageMapper matchMapper;

    public int insert(MatchImage matchImage){
        matchMapper.insert(matchImage);
        return matchImage.getMatchID();
    }

    public void insertWithMatchID(MatchImage matchImage){
        matchMapper.insertWithMatchID(matchImage);
    }

    //插入MathImage 返回MatchID 如果集合为空返回0
    public int insertList(List<MatchImage> list){
        if(list.size() != 0 ){
            MatchImage matchImage;
            matchMapper.insert(list.get(0));
            int key = list.get(0).getMatchID();
            for (int i = 1; i < list.size(); i++) {
                matchImage = list.get(i);
                matchImage.setMatchID(key);
                matchMapper.insertWithMatchID(matchImage);
            }
            return key;
        }else{
            return 0;
        }
    }

    public List<Integer> selectByMatchID(int mathchID){
        if(mathchID == 0 ){
            return null;
        }
        return matchMapper.selectByMatchID(mathchID);
    }
}
