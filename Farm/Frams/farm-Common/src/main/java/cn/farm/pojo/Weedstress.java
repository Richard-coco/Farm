package cn.farm.pojo;

public class Weedstress {
    private Integer weedid;

    private Integer stresstype;

    private Integer cropid;

    private Integer category;

    private String harm;

    private String symptom;

    private String control;

    private Integer symptomimage;

    public Integer getWeedid() {
        return weedid;
    }

    public void setWeedid(Integer weedid) {
        this.weedid = weedid;
    }

    public Integer getStresstype() {
        return stresstype;
    }

    public void setStresstype(Integer stresstype) {
        this.stresstype = stresstype;
    }

    public Integer getCropid() {
        return cropid;
    }

    public void setCropid(Integer cropid) {
        this.cropid = cropid;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getHarm() {
        return harm;
    }

    public void setHarm(String harm) {
        this.harm = harm == null ? null : harm.trim();
    }

    public String getSymptom() {
        return symptom;
    }

    public void setSymptom(String symptom) {
        this.symptom = symptom == null ? null : symptom.trim();
    }

    public String getControl() {
        return control;
    }

    public void setControl(String control) {
        this.control = control == null ? null : control.trim();
    }

    public Integer getSymptomimage() {
        return symptomimage;
    }

    public void setSymptomimage(Integer symptomimage) {
        this.symptomimage = symptomimage;
    }
}