package cn.farm.expert.utils;

import cn.farm.expert.domain.Model;
import org.apache.ibatis.jdbc.SQL;

public class ModelProvider {

    public String updateModel(final Model model){

        return new SQL(){{
            UPDATE("model");

            //条件写法.
            if(model.getAuthor()!= 0){
                SET("author=#{author}");
            }
            if(model.getDescription() != null) {
                SET("description = #{description}");
            }
            if(model.getModelId() != 0) {
                SET("modelId = #{modelId}");
            }
            if(model.getModelFile() != null) {
                SET("modelFile = #{modelFile}");
            }
            if(model.getModelName() != null) {
                SET("modelName = #{modelName}");
            }
            WHERE("modelId=#{modelId} and author = #{author}");
        }}.toString();
    }
    }
