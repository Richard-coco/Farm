package cn.farm.entity;

import lombok.Data;

@Data
public class Abioticstress {
    private Integer abioticid;

    private Integer stresstype;

    private Integer cropid;

    private String harm;

    private String symptom;

    private String control;

    private Integer symptomimage;

    public Abioticstress(Integer stresstype, Integer cropid, String harm, String symptom, String control, Integer symptomimage) {
        this.stresstype = stresstype;
        this.cropid = cropid;
        this.harm = harm;
        this.symptom = symptom;
        this.control = control;
        this.symptomimage = symptomimage;
    }
}