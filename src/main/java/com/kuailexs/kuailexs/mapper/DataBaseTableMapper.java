package com.kuailexs.kuailexs.mapper;

import com.kuailexs.common.mapper.MyBaseMapper;
import com.kuailexs.mirror.ubports.web.bean.Blog;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;


/**
 * @Author ：dhl
 * @Date ：Created in 14:17 2019/8/23
 * @Description：
 * @Modified By：
 * @Version:
 */
//@Repository
public interface DataBaseTableMapper {

    @Update("${tableSql}")
    void createTableBySql(@Param("tableSql") String tableSql);

    @Select("select count(1) from ${tableName} limit 1")
    Integer findOneId(@Param("tableName") String tableName);
}
