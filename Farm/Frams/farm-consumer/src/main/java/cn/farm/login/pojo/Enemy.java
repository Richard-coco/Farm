package cn.farm.login.pojo;

public class Enemy {
    private Integer enemyid;

    private String chinesename;

    private String latinname;

    private String alias;

    private Integer category;

    private String distribution;

    private String morphology;

    private Integer imageid;

    private String lifehabit;

    public Integer getEnemyid() {
        return enemyid;
    }

    public void setEnemyid(Integer enemyid) {
        this.enemyid = enemyid;
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

    public Integer getImageid() {
        return imageid;
    }

    public void setImageid(Integer imageid) {
        this.imageid = imageid;
    }

    public String getLifehabit() {
        return lifehabit;
    }

    public void setLifehabit(String lifehabit) {
        this.lifehabit = lifehabit == null ? null : lifehabit.trim();
    }
}