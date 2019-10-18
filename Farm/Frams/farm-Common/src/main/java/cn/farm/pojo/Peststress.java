package cn.farm.pojo;

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

    public Integer getPestid() {
        return pestid;
    }

    public void setPestid(Integer pestid) {
        this.pestid = pestid;
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

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getDistribution() {
        return distribution;
    }

    public void setDistribution(String distribution) {
        this.distribution = distribution == null ? null : distribution.trim();
    }

    public String getMorphology() {
        return morphology;
    }

    public void setMorphology(String morphology) {
        this.morphology = morphology == null ? null : morphology.trim();
    }

    public String getLifehabit() {
        return lifehabit;
    }

    public void setLifehabit(String lifehabit) {
        this.lifehabit = lifehabit == null ? null : lifehabit.trim();
    }

    public String getDamage() {
        return damage;
    }

    public void setDamage(String damage) {
        this.damage = damage == null ? null : damage.trim();
    }

    public String getControl() {
        return control;
    }

    public void setControl(String control) {
        this.control = control == null ? null : control.trim();
    }

    public Integer getPestimage() {
        return pestimage;
    }

    public void setPestimage(Integer pestimage) {
        this.pestimage = pestimage;
    }

    public Integer getSyptomimage() {
        return syptomimage;
    }

    public void setSyptomimage(Integer syptomimage) {
        this.syptomimage = syptomimage;
    }
}