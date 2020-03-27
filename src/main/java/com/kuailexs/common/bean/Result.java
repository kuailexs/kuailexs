package com.kuailexs.common.bean;

import lombok.Data;

/**
 * @Author ：dhl
 * @Date ：Created in 11:23 2020/1/22
 * @Description：
 * @Modified By：
 * @Version:
 */
@Data
public class Result<T> {
    final static Integer SUCCESS = 0;
    final static Integer FAIL = 1;
    //{
    //  "code": 0,
    //  "msg": "",
    //  "data":
    //}
    private Integer code;
    private String msg;
    private T data;
    public static Result success(){
        return success(null);
    }
    public static Result success(Object data){
        Result result = new Result();
        result.code = SUCCESS;
        result.msg = "成功";
        result.data = data;
        return result;
    }
    public static Result fail(){
        Result result = new Result();
        result.code = FAIL;
        result.msg = "失败";
        result.data = null;
        return result;
    }
}
