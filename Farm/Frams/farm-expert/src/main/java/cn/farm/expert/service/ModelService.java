package cn.farm.expert.service;
import cn.farm.expert.domain.Model;

import java.util.List;

public interface ModelService {

    List<Model> findByAuthor(int author);

    int findByModelId( int modelId);

    int save(Model model);

    int remove(int author,int modelId);

    int removeByModelId(int modelId);

    int update(Model model);

}
