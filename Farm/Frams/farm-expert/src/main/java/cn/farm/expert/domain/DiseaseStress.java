package cn.farm.expert.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DiseaseStress {

    private int diseaseId;

    private int stressType;

    private int cropId;

    private String chineseName;

    private String latinName;

    private String alias;

    private String distribution;

    private String symptom;

    private String pathogeny;

    private String occurrence;

    private String control;

    private String pathoImage;

    private String syptomImage;

}
