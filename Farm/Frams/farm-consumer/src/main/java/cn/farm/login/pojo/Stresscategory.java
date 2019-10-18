package cn.farm.login.pojo;

public class Stresscategory {
    private Integer stressid;

    private String name;

    private String maintype;

    private String subtype;

    private String factor;

    public Integer getStressid() {
        return stressid;
    }

    public void setStressid(Integer stressid) {
        this.stressid = stressid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getMaintype() {
        return maintype;
    }

    public void setMaintype(String maintype) {
        this.maintype = maintype == null ? null : maintype.trim();
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype == null ? null : subtype.trim();
    }

    public String getFactor() {
        return factor;
    }

    public void setFactor(String factor) {
        this.factor = factor == null ? null : factor.trim();
    }
}