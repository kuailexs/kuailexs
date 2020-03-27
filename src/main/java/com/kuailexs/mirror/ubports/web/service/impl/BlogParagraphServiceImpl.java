package com.kuailexs.mirror.ubports.web.service.impl;

import com.kuailexs.mirror.ubports.web.bean.BlogParagraph;
import com.kuailexs.common.service.impl.BaseServiceImpl;
import com.kuailexs.mirror.ubports.web.mapper.BlogParagraphMapper;
import com.kuailexs.common.mapper.MyBaseMapper;
import com.kuailexs.mirror.ubports.web.service.BlogParagraphService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Author ：dhl
 * @Date ：Created in 14:37 2019/8/23
 * @Description：
 * @Modified By：
 * @Version:
 */
@Service
public class BlogParagraphServiceImpl extends BaseServiceImpl<BlogParagraph,Integer> implements BlogParagraphService {

    @Autowired
    BlogParagraphMapper blogParagraphMapper;

    @Override
    public BlogParagraph getNextGoogleTranslateBlogParagraph() {
        Example example = new Example(BlogParagraph.class);
        example.createCriteria()
                .andEqualTo("googleTranslated",0);
        RowBounds rowBounds = new RowBounds(0,1);
        List<BlogParagraph> list = blogParagraphMapper.selectByExampleAndRowBounds(example,rowBounds);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public BlogParagraph getNextYoudaoTranslateBlogParagraph() {
        Example example = new Example(BlogParagraph.class);
        example.createCriteria()
                .andEqualTo("youdaoTranslated",0);
        RowBounds rowBounds = new RowBounds(0,1);
        List<BlogParagraph> list = blogParagraphMapper.selectByExampleAndRowBounds(example,rowBounds);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public MyBaseMapper<BlogParagraph> getBaseMapper() {
        return blogParagraphMapper;
    }
}
