package cn.farm.expert.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Category {

    private int bioId;

    private String kingdom;

    private String phylum;

    private String bioClass;

    private String order;

    private String family;

    private String genus;

    private String species;

}
