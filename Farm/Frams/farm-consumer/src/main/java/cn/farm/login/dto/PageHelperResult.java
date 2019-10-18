package cn.farm.login.dto;

import java.util.List;

public class PageHelperResult<T> {
    private List<T> list;
    private Integer number;

    public PageHelperResult() {}

    public PageHelperResult(List<T> list, Integer number) {
        this.list = list;
        this.number = number;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
