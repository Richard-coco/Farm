package cn.farm.entity;

import lombok.Data;
import org.apache.ibatis.annotations.Options;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "image")
public class Image {
    @Column(name = "imageID")
    private Integer imageid;

    private String name;

    private String format;

    private String description;

    private String author;

    private Date time;

    private String location;

    private String device;

    private String imagefile;

    private Match_Image matchImage;
}