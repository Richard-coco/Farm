package cn.farm.entity;

import lombok.Data;

@Data
public class Model {
    private Integer modelid;

    private String modelname;

    private Integer author;

    private String modelfile;

    private String description;

    public Model(String modelname, Integer author, String modelfile, String description) {
        this.modelname = modelname;
        this.author = author;
        this.modelfile = modelfile;
        this.description = description;
    }
}