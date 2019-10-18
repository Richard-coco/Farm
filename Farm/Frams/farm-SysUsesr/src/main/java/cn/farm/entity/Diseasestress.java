package cn.farm.entity;

import lombok.Data;

@Data
public class Diseasestress {
    private Integer diseaseid;

    private Integer stresstype;

    private Integer cropid;

    private String chinesename;

    private String latinname;

    private String alias;

    private String distribution;

    private String symptom;

    private String pathogeny;

    private String occurrence;

    private String control;

    private Integer pathoimage;

    private Integer symptomimage;


    public Diseasestress(Integer stresstype,Integer cropid,String chinesename, String latinname, String alias, String distribution,
                         String symptom, String pathogeny, String occurrence, String control,
                         Integer pathoimage, Integer symptomimage) {
        this.stresstype = stresstype;
        this.cropid = cropid;
        this.chinesename = chinesename;
        this.latinname = latinname;
        this.alias =alias;
        this.distribution = distribution;
        this.symptom = symptom;
        this.pathogeny = pathogeny;
        this.occurrence =occurrence;
        this.control = control;
        this.pathoimage = pathoimage;
        this.symptomimage = symptomimage;
    }
}