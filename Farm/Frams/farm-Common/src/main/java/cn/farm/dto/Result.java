package cn.farm.dto;
/**
 * 封装json对象，所有返回结果都使用它
 */
public class Result<T> {
    private Integer code;// 状态码

    private T data;// 成功时返回的数据

    private String message;// 错误信息

    public Result() {
    }

    // 成功时的构造器
    public Result(Integer code, T data,String message){
        this.code = code;
        this.data = data;
        this.message = message;
    }

    // 错误时的构造器
    public Result(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    // 需要分页时的构造器时的构造器
  //  public Result(Integer code, T data,Integer all,String message){
//        this.code = code;
  //      this.data = data;
   //     this.message = message;
   //     this.all = all;
 //   }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String error) {
        this.message = error;
    }

}
