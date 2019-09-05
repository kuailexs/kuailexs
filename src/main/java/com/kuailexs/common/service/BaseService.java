package com.kuailexs.common.service;

import com.kuailexs.common.mapper.MyBaseMapper;

import java.util.List;

/**
 * @Author ：dhl
 * @Date ：Created in 14:44 2019/8/23
 * @Description：
 * @Modified By：
 * @Version:
 */
public interface BaseService<T,K> {



    List<T> list();
    T getOne(K id);
    int save(T t);
    int delete(K id);
    int update(T t);

    T selectOneByExample(Object example);

    List<T> selectByExample(Object example);

    MyBaseMapper<T> getBaseMapper();
}
