package cn.farm.pojo;

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

    public Integer getDiseaseid() {
        return diseaseid;
    }

    public void setDiseaseid(Integer diseaseid) {
        this.diseaseid = diseaseid;
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

    public String getChinesename() {
        return chinesename;
    }

    public void setChinesename(String chinesename) {
        this.chinesename = chinesename == null ? null : chinesename.trim();
    }

    public String getLatinname() {
        return latinname;
    }

    public void setLatinname(String latinname) {
        this.latinname = latinname == null ? null : latinname.trim();
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias == null ? null : alias.trim();
    }

    public String getDistribution() {
        return distribution;
    }

    public void setDistribution(String distribution) {
        this.distribution = distribution == null ? null : distribution.trim();
    }

    public String getSymptom() {
        return symptom;
    }

    public void setSymptom(String symptom) {
        this.symptom = symptom == null ? null : symptom.trim();
    }

    public String getPathogeny() {
        return pathogeny;
    }

    public void setPathogeny(String pathogeny) {
        this.pathogeny = pathogeny == null ? null : pathogeny.trim();
    }

    public String getOccurrence() {
        return occurrence;
    }

    public void setOccurrence(String occurrence) {
        this.occurrence = occurrence == null ? null : occurrence.trim();
    }

    public String getControl() {
        return control;
    }

    public void setControl(String control) {
        this.control = control == null ? null : control.trim();
    }

    public Integer getPathoimage() {
        return pathoimage;
    }

    public void setPathoimage(Integer pathoimage) {
        this.pathoimage = pathoimage;
    }

    public Integer getSymptomimage() {
        return symptomimage;
    }

    public void setSymptomimage(Integer symptomimage) {
        this.symptomimage = symptomimage;
    }
}