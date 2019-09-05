package com.kuailexs.mirror.ubports.web.service.impl;

import com.kuailexs.mirror.ubports.web.bean.BlogParagraph;
import com.kuailexs.mirror.ubports.web.bean.BlogSection;
import com.kuailexs.common.mapper.MyBaseMapper;
import com.kuailexs.common.service.impl.BaseServiceImpl;
import com.kuailexs.mirror.ubports.web.mapper.BlogParagraphMapper;
import com.kuailexs.mirror.ubports.web.mapper.BlogSectionMapper;
import com.kuailexs.mirror.ubports.web.service.BlogSectionService;
import com.kuailexs.mirror.ubports.web.vo.BlogSectionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    @Autowired
    BlogParagraphMapper blogParagraphMapper;

    @Override
    public int save(BlogSection blogSection) {
        int result = super.save(blogSection);
        //如果是带子集的
        if(blogSection instanceof BlogSectionVo){
            List<BlogParagraph> blogParagraphList = ((BlogSectionVo) blogSection).getBlogParagraphList();
            for (BlogParagraph blogParagraph : blogParagraphList){
                blogParagraph.setSectionId(blogSection.getId());
                blogParagraph.setBlogId(blogSection.getBlogId());
                blogParagraphMapper.insertUseGeneratedKeys(blogParagraph);
            }
        }
        return result;
    }

    @Override
    public MyBaseMapper<BlogSection> getBaseMapper() {
        return blogSectionMapper;
    }
}
