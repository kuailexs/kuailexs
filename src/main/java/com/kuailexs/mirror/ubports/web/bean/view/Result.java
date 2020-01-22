package com.kuailexs.mirror.ubports.web.bean.view;

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
    //{
    //  "code": 0,
    //  "msg": "",
    //  "data":
    //}
    private Integer code;
    private String msg;
    private T data;
}
