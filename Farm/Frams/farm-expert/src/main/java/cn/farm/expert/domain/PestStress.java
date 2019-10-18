package cn.farm.expert.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PestStress {

    private int pestId;

    private int stressType;

    private int cropId;

    private String chineseName;

    private String latinName;

    private String alias;

    private int category;

    private String distribution;

    private String morphology;

    private String lifeHabit;

    private String damage;

    private String control;

    private String pestImage;

    private String syptomImage;

    private Category categoryy;

}
