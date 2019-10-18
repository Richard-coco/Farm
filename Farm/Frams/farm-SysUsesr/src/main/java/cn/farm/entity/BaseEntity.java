package cn.farm.entity;

import javax.persistence.Transient;

public class BaseEntity {
    @Transient
    private Integer page = 1;

    @Transient
    private Integer rows = 3;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }
}
