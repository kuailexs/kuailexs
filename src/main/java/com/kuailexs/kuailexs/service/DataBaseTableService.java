package com.kuailexs.kuailexs.service;

/**
 * 数据库表操作
 */
public interface DataBaseTableService {

    /**
     * 检查表是否存在
     * 查询一次，根据报错信息判断
     * @param tableName
     * @return
     */
    boolean checkTableExits(String tableName);

    /**
     * 创建表 ，传入完整表创建语句
     * @param tableSql
     */
    void createTableBySql(String tableSql);
}
