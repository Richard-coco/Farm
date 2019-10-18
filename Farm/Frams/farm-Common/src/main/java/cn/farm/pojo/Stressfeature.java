package cn.farm.pojo;

public class Stressfeature {
    private Integer stessid;

    private Integer stresstype;

    private String paramfile;

    private String vectfile;

    public Integer getStessid() {
        return stessid;
    }

    public void setStessid(Integer stessid) {
        this.stessid = stessid;
    }

    public Integer getStresstype() {
        return stresstype;
    }

    public void setStresstype(Integer stresstype) {
        this.stresstype = stresstype;
    }

    public String getParamfile() {
        return paramfile;
    }

    public void setParamfile(String paramfile) {
        this.paramfile = paramfile == null ? null : paramfile.trim();
    }

    public String getVectfile() {
        return vectfile;
    }

    public void setVectfile(String vectfile) {
        this.vectfile = vectfile == null ? null : vectfile.trim();
    }
}