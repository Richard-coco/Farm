package cn.farm.entity;

import lombok.Data;
import javax.persistence.Table;
import java.util.List;

@Data
@Table(name = "match_image")
public class Match_Image {
    private Integer matchid;
   // @Id
    private Integer imageid;
    private String matchinfo;

    private Image image;

    public Match_Image(Integer plantimage, String matchinfo) {
           this.imageid =  plantimage;
           this.matchinfo = matchinfo;
       }

    public Match_Image() {

    }

    public void getMatchid(List<Image> images) {
    }
}
