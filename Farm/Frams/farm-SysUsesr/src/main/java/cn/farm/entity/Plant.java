package cn.farm.entity;

import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "plant")
public class Plant{
    @Id
    @Column(name = "plantID")
    private Integer plantid;

    private String chinesename;

    private String latinname;

    private String alias;

    private Integer category;

    private String distribution;

    private String morphology;
    @Column(name = "plantImage")
    private Integer plantimage;

    private String growthhabit;

    public Plant(String chinesename, String latinname, String alias, Integer category, String distribution, String morphology, Integer plantimage, String growthhabit) {
        this.chinesename = chinesename;
        this.latinname = latinname;
        this.alias = alias;
        this.category = category;
        this.distribution = distribution;
        this.morphology = morphology;
        this.plantimage = plantimage;
        this.growthhabit = growthhabit;
    }

    public Plant(Integer plantimage) {

    }


    @Override
      public String toString() {
          return "Plant{" +
                  "plantid=" + plantid +
                  ", chinesename='" + chinesename + '\'' +
                  ", latinname='" + latinname + '\'' +
                  ", alias='" + alias + '\'' +
                  ", category=" + category +
                  ", distribution='" + distribution + '\'' +
                  ", norphology='" + morphology + '\'' +
                  ", plantimage=" + plantimage +
                  ", growthhabit='" + growthhabit + '\'' +
                  '}';
      }

}