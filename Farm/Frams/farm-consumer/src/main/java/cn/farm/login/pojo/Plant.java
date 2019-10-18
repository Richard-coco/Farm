package cn.farm.login.pojo;

public class Plant {
    private Integer plantid;

    private String chinesename;

    private String latinname;

    private String alias;

    private Integer category;

    private String distribution;

    private String morphology;

    private Integer plantimage;

    private String growthhabit;

    public Integer getPlantid() {
        return plantid;
    }

    public void setPlantid(Integer plantid) {
        this.plantid = plantid;
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

    public Integer getPlantimage() {
        return plantimage;
    }

    public void setPlantimage(Integer plantimage) {
        this.plantimage = plantimage;
    }

    public String getGrowthhabit() {
        return growthhabit;
    }

    public void setGrowthhabit(String growthhabit) {
        this.growthhabit = growthhabit == null ? null : growthhabit.trim();
    }
}