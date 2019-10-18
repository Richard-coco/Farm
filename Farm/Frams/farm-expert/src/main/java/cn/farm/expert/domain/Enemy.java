package cn.farm.expert.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Enemy {

    private int enemyId;

    private String chineseName;

    private String latinName;

    private String alias;

    private int category;

    private String distribution;

    private String morphology;

    private String enemyImage;

    private String lifeHabit;

    private Category categoryy;

}
