package com.kuailexs.kuailexs.service.impl;

import com.kuailexs.kuailexs.mapper.DataBaseTableMapper;
import com.kuailexs.kuailexs.service.DataBaseTableService;
import com.kuailexs.mirror.ubports.web.mapper.BlogParagraphMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.SQLSyntaxErrorException;

/**
 * @Author ：dhl
 * @Date ：Created in 14:45 2019/8/23
 * @Description：
 * @Modified By：
 * @Version:
 */
@Service
public class DataBaseTableServiceImpl implements DataBaseTableService {
    @Resource
    DataBaseTableMapper dataBaseTableMapper;

    /**
     * 检查表是否存在
     * 查询一次，根据报错信息判断
     * @param tableName
     * @return
     */
    @Override
    public boolean checkTableExits(String tableName) {
        try {
            dataBaseTableMapper.findOneId(tableName);
        }catch (Exception e){
            Throwable e1 =  e.getCause();
            if(e1 instanceof SQLSyntaxErrorException){
                SQLSyntaxErrorException errorException = (SQLSyntaxErrorException) e1;
                String state = errorException.getSQLState();
                //mysql的表不存在状态码
                if("42S02".equals(state)){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 创建表 ，传入完整表创建语句
     * @param tableSql
     */
    @Override
    public void createTableBySql(String tableSql) {
        dataBaseTableMapper.createTableBySql(tableSql);
    }
}
