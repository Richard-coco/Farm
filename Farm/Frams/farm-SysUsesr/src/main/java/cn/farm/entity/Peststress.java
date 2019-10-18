package cn.farm.entity;

import lombok.Data;

@Data
public class Peststress {
    private Integer pestid;

    private Integer stresstype;

    private Integer cropid;

    private String chinesename;

    private String latinname;

    private String alias;

    private Integer category;

    private String distribution;

    private String morphology;

    private String lifehabit;

    private String damage;

    private String control;

    private Integer pestimage;

    private Integer syptomimage;

    public Peststress(Integer stresstype, Integer cropid, String chinesename, String latinname, String alias, Integer category, String distribution, String morphology, String lifehabit, String damage, String control, Integer pestimage, Integer syptomimage) {
        this.stresstype = stresstype;
        this.cropid = cropid;
        this.chinesename = chinesename;
        this.latinname = latinname;
        this.alias = alias;
        this.category = category;
        this.distribution = distribution;
        this.morphology = morphology;
        this.lifehabit = lifehabit;
        this.damage = damage;
        this.control = control;
        this.pestimage = pestimage;
        this.syptomimage = syptomimage;
    }
}