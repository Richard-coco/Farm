package cn.farm.entity;

import lombok.Data;

import javax.persistence.Column;

@Data
public class Enemy {
    private Integer enemyid;

    private String chinesename;

    private String latinname;

    private String alias;

    private Integer category;

    private String distribution;

    private String morphology;

    private Integer imageid;

    @Column(name = "lifeHabit")
    private String lifehabit;


    public Enemy(String chinesename, String latinname, String alias, Integer category, String distribution, String morphology, Integer imageid, String lifehabit) {
        this.chinesename = chinesename;
        this.latinname = latinname;
        this.alias = alias;
        this.category = category;
        this.distribution = distribution;
        this.morphology = morphology;
        this.imageid = imageid;
        this.lifehabit = lifehabit;
    }
}