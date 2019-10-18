package cn.farm.Entity.SysUser;

import lombok.Data;

@Data
public class Stressfeature {
    private Integer stessid;

    private Integer stresstype;

    private String paramfile;

    private String vectfile;

    public Stressfeature(Integer stresstype, String paramfile, String vectfile) {
        this.stresstype = stresstype;
        this.paramfile = paramfile;
        this.vectfile = vectfile;
    }
}