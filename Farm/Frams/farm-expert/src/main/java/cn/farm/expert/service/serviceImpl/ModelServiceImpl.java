package cn.farm.expert.service.serviceImpl;

import cn.farm.expert.domain.Model;
import cn.farm.expert.mapper.ModelMapper;
import cn.farm.expert.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModelServiceImpl implements ModelService {

    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<Model> findByAuthor(int author) {
        List<Model> modelList = modelMapper.findByAuthor(author);
        return modelList;
    }

    @Override
    public int findByModelId(int modelId) {
        int sum = modelMapper.findByModelId(modelId);
        return sum;
    }

    @Override
    public int save(Model model) {

        int sum = modelMapper.add(model);
        return sum;
    }

    @Override
    public int remove(int author,int modelId) {

        int sum = modelMapper.remove(author,modelId);
        return sum;

    }

    @Override
    public int removeByModelId(int modelId) {
        int sum = modelMapper.removeByModelId(modelId);
        return sum;
    }

    @Override
    public int update(Model model) {
        int sum = modelMapper.update(model);
        return sum;
    }
}
