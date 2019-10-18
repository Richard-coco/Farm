package cn.farm.expert.domain;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.pagehelper.PageInfo;

import java.util.List;


@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class JsonData  {

	private static final long serialVersionUID = 1L;

	private  long totalCount;

	private  int pageSize;

	private  int PageNum;

	private Data jsonData; // 数据

	public JsonData() {

	}

	public JsonData(Data data) {
		this.jsonData = data;

	}

	public JsonData(Data data,long totalCount,int pageSize,int PageNum) {
		this.jsonData = data;
		this.totalCount = totalCount;
		this.pageSize = pageSize;
		this.PageNum = PageNum;
	}

	// 查询成功，传入数据
	public  JsonData selectSuccess(Data data,int pageNum,int pageSize1) {
		PageInfo  pageInfo = new PageInfo((List) data.getData());
		pageInfo.setPageNum(pageNum);
		pageInfo.setPageSize(pageSize1);
		return new JsonData( data,pageInfo.getTotal(),pageSize1,pageNum);
	}

	// 查询失败
	public static JsonData selectFailure(Data data) { return new JsonData(data); }
	// 新建或者修改数据成功
	public static JsonData creatSuccess(Data data) {return new JsonData(data);}

	// 用户删除数据成功 返回删除行数
	public static JsonData deleteSuccess(Data data) {
		return new JsonData(data);
	}

	// 客户端发送的请求错误
	public static JsonData requestError(Data data) {
		return new JsonData(data);
	}

	// 用户请求的资源，服务器未找到
	public static JsonData undeFined(Data data) {
		return new JsonData(data);
	}

	// 服务器错误
	public static JsonData serverError(Data data) {
		return new JsonData(data);
	}


	public Object getData() {
		return jsonData;
	}

	public void setData(Data data) {
		this.jsonData = data;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getPageNum() {
		return PageNum;
	}

}
