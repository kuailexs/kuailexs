package com.kuailexs.iptv.bean.view;

import lombok.Data;

/**
 * 提交结果
 */
@Data
public class CheckStatistics {


    Integer total; //总计
    Integer checked;//已检查
    Integer unableCheck;//无法检查
    Integer unchecked;//未检查
    Integer available;//有效的
    Integer unavailable;//无效的
    Integer excellent;// 优质的
    Integer unexcellent;//不好的

    Integer checking;//检查中

}
