package cn.farm.pojo;

public class Abioticstress {
    private Integer abioticid;

    private Integer stresstype;

    private Integer cropid;

    private String harm;

    private String symptom;

    private String control;

    private Integer symptomimage;

    public Integer getAbioticid() {
        return abioticid;
    }

    public void setAbioticid(Integer abioticid) {
        this.abioticid = abioticid;
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