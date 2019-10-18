package cn.farm.entity;

import lombok.Data;

@Data
public class TpRegion2 {
    private Integer id;

    private String name;

    private Integer parentId;

    private Boolean level;

}