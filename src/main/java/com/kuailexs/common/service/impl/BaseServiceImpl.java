package com.kuailexs.common.service.impl;

import com.kuailexs.common.mapper.MyBaseMapper;
import com.kuailexs.common.service.BaseService;

import java.util.List;

/**
 * @Author ：dhl
 * @Date ：Created in 14:45 2019/8/23
 * @Description：
 * @Modified By：
 * @Version:
 */
public abstract class BaseServiceImpl<T,K> implements BaseService<T,K> {

    public abstract MyBaseMapper<T> getBaseMapper();

    @Override
    public List<T> list() {
        return getBaseMapper().selectAll();
    }

    @Override
    public T getOne(K id) {
        return (T) getBaseMapper().selectByPrimaryKey(id);
    }

    @Override
    public int save(T t) {
        return getBaseMapper().insertUseGeneratedKeys(t);
    }

    @Override
    public int delete(K id) {
        return getBaseMapper().deleteByPrimaryKey(id);
    }

    @Override
    public int update(T t) {
        return getBaseMapper().updateByPrimaryKeySelective(t);
    }

    @Override
    public T selectOneByExample(Object example) {
        return getBaseMapper().selectOneByExample(example);
    }

    @Override
    public List<T> selectByExample(Object example) {
        return getBaseMapper().selectByExample(example);
    }
}
