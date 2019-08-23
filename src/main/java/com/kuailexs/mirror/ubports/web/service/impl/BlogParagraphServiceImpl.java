package com.kuailexs.mirror.ubports.web.service.impl;

import com.kuailexs.mirror.ubports.web.bean.BlogParagraph;
import com.kuailexs.mirror.ubports.web.mapper.BlogParagraphMapper;
import com.kuailexs.mirror.ubports.web.mapper.MyBaseMapper;
import com.kuailexs.mirror.ubports.web.service.BlogParagraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author ：dhl
 * @Date ：Created in 14:37 2019/8/23
 * @Description：
 * @Modified By：
 * @Version:
 */
@Service("blogParagraphService")
public class BlogParagraphServiceImpl extends BaseServiceImpl<BlogParagraph,Integer>  implements BlogParagraphService {

    @Autowired
    BlogParagraphMapper blogParagraphMapper;

    @Override
    public MyBaseMapper<BlogParagraph> getBaseMapper() {
        return blogParagraphMapper;
    }
}
