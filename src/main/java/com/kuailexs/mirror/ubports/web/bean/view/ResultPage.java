package com.kuailexs.mirror.ubports.web.bean.view;

import lombok.Data;

import java.util.List;

/**
 * @Author ：dhl
 * @Date ：Created in 11:25 2020/1/22
 * @Description：
 * @Modified By：
 * @Version:
 */
@Data
public class ResultPage<T>{
    //{
    //  "code": 0,
    //  "msg": "",
    //  "count": 1000,
    //  "data": [{}, {}]
    //  "totalRow": {
    //    "score": "666"
    //    ,"experience": "999"
    //  }

    private Integer code;
    private String msg;
    private Long count;
    private List<T> data;
    private TotalRow totalRow;

    @Data
    public static class TotalRow {
        private String score;
        private String experience;
    }
}
