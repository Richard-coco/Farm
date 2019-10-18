package cn.farm.Entity.SysUser;

import lombok.Data;

import java.util.Date;

@Data
public class Spectral {
    private Integer spectralid;

    private String name;

    private String format;

    private String description;

    private String author;

    private Date time;

    private String location;

    private String device;

    private String spectralfile;

    public Spectral(String name, String format, String description, String author, Date time, String location, String device, String spectralfile) {
        this.name = name;
        this.format = format;
        this.description = description;
        this.author = author;
        this.time = time;
        this.location = location;
        this.device = device;
        this.spectralfile = spectralfile;
    }
}