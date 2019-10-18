package cn.farm.pojo;

public class Rule {
    private Integer ruleid;

    private String rulename;

    private Integer author;

    private Integer stresstype;

    private Integer cropid;

    private String rulefile;

    private String description;

    public Integer getRuleid() {
        return ruleid;
    }

    public void setRuleid(Integer ruleid) {
        this.ruleid = ruleid;
    }

    public String getRulename() {
        return rulename;
    }

    public void setRulename(String rulename) {
        this.rulename = rulename == null ? null : rulename.trim();
    }

    public Integer getAuthor() {
        return author;
    }

    public void setAuthor(Integer author) {
        this.author = author;
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

    public String getRulefile() {
        return rulefile;
    }

    public void setRulefile(String rulefile) {
        this.rulefile = rulefile == null ? null : rulefile.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}