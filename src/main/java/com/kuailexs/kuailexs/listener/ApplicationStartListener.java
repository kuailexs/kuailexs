package com.kuailexs.kuailexs.listener;

import com.kuailexs.common.annotation.TableSql;
import com.kuailexs.kuailexs.service.DataBaseTableService;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.persistence.Table;
import java.util.Set;

@Component
@Slf4j
public class ApplicationStartListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    DataBaseTableService dataBaseTableService;
    /**
     * 初始启动quartz
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            //入参 要扫描的包名
            Reflections f = new Reflections("com.kuailexs");
            //入参 目标注解类 不包含继承的类
            Set<Class<?>> set = f.getTypesAnnotatedWith(TableSql.class, true);
            for (Class<?> c : set) {
                TableSql annotationTableSql = c.getAnnotation(TableSql.class);
                Table annotationTable = c.getAnnotation(Table.class);
                String sqlValue = annotationTableSql.value();
                String tableValue = annotationTable.name();
                boolean exits = dataBaseTableService.checkTableExits(tableValue);
                if (!exits) {
                    //不存在数据库表 马上新建
                    dataBaseTableService.createTableBySql(sqlValue);
                }
            }
            log.info("检查数据库表创建处理成功！");
        }catch (Exception e){
            log.error("检查数据库表创建处理失败！",e);
            e.printStackTrace();
        }
    }
}