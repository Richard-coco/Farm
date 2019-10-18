package cn.farm.expert.domain;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Diagnosis {

    private int inquiryId;

    private int ruleId;

    private int modelId;

    private int type;

    private int degree;
}
