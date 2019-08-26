package com.kuailexs.mirror.ubports.web.service.impl;

import com.kuailexs.mirror.ubports.web.bean.BlogSection;
import com.kuailexs.mirror.ubports.web.common.mapper.MyBaseMapper;
import com.kuailexs.mirror.ubports.web.common.service.impl.BaseServiceImpl;
import com.kuailexs.mirror.ubports.web.mapper.BlogSectionMapper;
import com.kuailexs.mirror.ubports.web.service.BlogSectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author ：dhl
 * @Date ：Created in 14:37 2019/8/23
 * @Description：
 * @Modified By：
 * @Version:
 */
@Service("blogSectionService")
public class BlogSectionServiceImpl extends BaseServiceImpl<BlogSection,Integer> implements BlogSectionService {

    @Autowired
    BlogSectionMapper blogSectionMapper;

    @Override
    public MyBaseMapper<BlogSection> getBaseMapper() {
        return blogSectionMapper;
    }
}
