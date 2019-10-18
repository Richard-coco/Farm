package cn.farm.entity;

import lombok.Data;

@Data
public class Rule {
    private Integer ruleid;

    private String rulename;

    private Integer author;

    private Integer stresstype;

    private Integer cropid;

    private String rulefile;

    private String description;

    public Rule(String rulename, Integer author, Integer stresstype, Integer cropid, String rulefile, String description) {
        this.rulename = rulename;
        this.author = author;
        this.stresstype = stresstype;
        this.cropid = cropid;
        this.rulefile = rulefile;
        this.description = description;
    }
}