package cn.qihangerp.common;

import java.io.Serializable;

/**
 * 描述：
 *
 * @author qlp
 * @date 2019-01-07 11:53 AM
 */
public class ApiResult<T> implements Serializable {
    private int code;
    private String msg;
    private T data;

    public ApiResult() {
//        this.code = ApiResultEnum.SUCCESS.getIndex();
//        msg = "SUCCESS";
    }
    public static <T> ApiResult<T> success(){
        ApiResult<T> result = new ApiResult<>();
        result.setCode(ApiResultEnum.SUCCESS.getIndex());
        result.setMsg("SUCCESS");
        return result;
    }
    public static <T> ApiResult<T> success(T data){
        ApiResult<T> result = new ApiResult<>();
        result.setCode(ApiResultEnum.SUCCESS.getIndex());
        result.setMsg("SUCCESS");
        result.setData(data);
        return result;
    }

    public ApiResult(ApiResultEnum result, String msg) {
        this.code = result.getIndex();
        this.msg = msg;
    }

    public ApiResult(ApiResultEnum result) {
        this.code = result.getIndex();
        this.msg = result.getName();
    }

    public ApiResult(ApiResultEnum result, T data) {
        this.code = result.getIndex();
        this.msg = result.getName();
        this.data = data;
    }

    public ApiResult(ApiResultEnum result, String msg, T data) {
        this.code = result.getIndex();
        this.msg = msg;
        this.data = data;
    }

    public ApiResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ApiResult(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static ApiResult ok(){
        return new ApiResult(ApiResultEnum.SUCCESS,"SUCCESS");
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
