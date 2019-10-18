package cn.farm.MessgaeModel;

import com.github.pagehelper.PageInfo;

import java.io.Serializable;

//implements Serializable
public class PageHelperMessage<T> extends Common implements Serializable {

   PageInfo pageInfo;

    public PageHelperMessage(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public PageHelperMessage(int code, String message, PageInfo pageInfo) {
        super(code, message);
        this.pageInfo = pageInfo;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }
}
