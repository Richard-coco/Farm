package cn.farm.expert.domain;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.pagehelper.PageInfo;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class JsonData1 {

    private Object data;

    private  long totalCount;

    private  int pageSize;

    private  int PageNum;

    private PageInfo pageInfo;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
        pageInfo = new PageInfo((List) data);
        this.totalCount =  pageInfo.getTotal();
    }

    public void setData(int sum) {
        this.data = sum;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Object data) {
        PageInfo pageInfo = new PageInfo((List) data);
        this.totalCount =  pageInfo.getTotal();
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
        pageInfo.setPageSize(pageSize);
    }

    public int getPageNum() {
        return PageNum;
    }

    public void setPageNum(int pageNum) {
        PageNum = pageNum;
        pageInfo.setPageNum(pageNum);
    }
}
