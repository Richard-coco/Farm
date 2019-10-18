package cn.farm.login.pojo;

import lombok.Data;

import javax.persistence.Table;

@Data
@Table(name = "match_image")
public class MatchImage {
    private Integer matchid;

    private Integer imageid;

    private String matchinfo;

    public Integer getMatchid() {
        return matchid;
    }

    public void setMatchid(Integer matchid) {
        this.matchid = matchid;
    }

    public Integer getImageid() {
        return imageid;
    }

    public void setImageid(Integer imageid) {
        this.imageid = imageid;
    }

    public String getMatchinfo() {
        return matchinfo;
    }

    public void setMatchinfo(String matchinfo) {
        this.matchinfo = matchinfo == null ? null : matchinfo.trim();
    }
}