package cn.farm.Entity.SysUser;

import lombok.Data;

@Data
public class Weedstress {
    private Integer weedid;

    private Integer stresstype;

    private Integer cropid;

    private Integer category;

    private String harm;

    private String symptom;

    private String control;

    private Integer symptomimage;

    public Weedstress(Integer stresstype, Integer cropid, Integer category, String harm, String symptom, String control, Integer symptomimage) {
        this.stresstype = stresstype;
        this.cropid = cropid;
        this.category = category;
        this.harm = harm;
        this.symptom = symptom;
        this.control = control;
        this.symptomimage = symptomimage;
    }
}