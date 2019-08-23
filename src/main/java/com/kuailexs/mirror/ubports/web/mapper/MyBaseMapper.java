package com.kuailexs.mirror.ubports.web.mapper;

import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @Author ：dhl
 * @Date ：Created in 14:55 2019/8/23
 * @Description：
 * @Modified By：
 * @Version:
 */
@Repository
public interface MyBaseMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
