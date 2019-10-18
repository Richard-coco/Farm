package cn.farm.entity;

import lombok.Data;

@Data
public class Stresscategory {
    private Integer stressid;

    private String name;

    private String maintype;

    private String subtype;

    private String factor;


    public Stresscategory(String name, String maintype, String subtype, String factor) {
        this.name = name;
        this.maintype = maintype;
        this.subtype = subtype;
        this.factor = factor;
    }
}