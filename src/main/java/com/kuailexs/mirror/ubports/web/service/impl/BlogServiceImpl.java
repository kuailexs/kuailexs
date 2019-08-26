package com.kuailexs.mirror.ubports.web.service.impl;

import com.kuailexs.mirror.ubports.web.bean.Blog;
import com.kuailexs.mirror.ubports.web.common.mapper.MyBaseMapper;
import com.kuailexs.mirror.ubports.web.common.service.impl.BaseServiceImpl;
import com.kuailexs.mirror.ubports.web.mapper.BlogMapper;
import com.kuailexs.mirror.ubports.web.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author ：dhl
 * @Date ：Created in 14:37 2019/8/23
 * @Description：
 * @Modified By：
 * @Version:
 */
@Service("blogService")
public class BlogServiceImpl extends BaseServiceImpl<Blog,Integer> implements BlogService {

    @Autowired
    BlogMapper blogMapper;

    @Override
    public MyBaseMapper<Blog> getBaseMapper() {
        return blogMapper;
    }
}
