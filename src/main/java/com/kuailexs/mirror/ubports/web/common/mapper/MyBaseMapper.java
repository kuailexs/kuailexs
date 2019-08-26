package com.kuailexs.mirror.ubports.web.common.mapper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 这个类不能被 tk.mybatis.spring.annotation.MapperScan 扫描到
 * @Author ：dhl
 * @Date ：Created in 14:55 2019/8/23
 * @Description：
 * @Modified By：
 * @Version:
 */
public interface MyBaseMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
