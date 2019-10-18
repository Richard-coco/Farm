package cn.farm.entity;

import lombok.Data;

@Data
public class Diagnosis {
    private Integer inquiryid;

    private Integer ruleid;

    private Integer modelid;

    private Integer type;

    private Integer degree;

    public Diagnosis(Integer inquiryid, Integer ruleid, Integer modelid, Integer type, Integer degree) {
        this.inquiryid = inquiryid;
        this.ruleid = ruleid;
        this.modelid = modelid;
        this.type = type;
        this.degree = degree;
    }
}