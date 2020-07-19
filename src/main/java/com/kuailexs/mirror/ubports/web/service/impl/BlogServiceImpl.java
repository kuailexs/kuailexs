package com.kuailexs.mirror.ubports.web.service.impl;

import com.kuailexs.mirror.ubports.web.bean.Blog;
import com.kuailexs.common.mapper.MyBaseMapper;
import com.kuailexs.common.service.impl.BaseServiceImpl;
import com.kuailexs.mirror.ubports.web.bean.BlogSection;
import com.kuailexs.mirror.ubports.web.mapper.BlogMapper;
import com.kuailexs.mirror.ubports.web.mapper.BlogParagraphMapper;
import com.kuailexs.mirror.ubports.web.mapper.BlogSectionMapper;
import com.kuailexs.mirror.ubports.web.service.BlogSectionService;
import com.kuailexs.mirror.ubports.web.service.BlogService;
import com.kuailexs.mirror.ubports.web.bean.view.BlogVo;
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
public class BlogServiceImpl extends BaseServiceImpl<Blog,Integer> implements BlogService {

    @Autowired
    BlogMapper blogMapper;
    @Autowired
    BlogParagraphMapper blogParagraphMapper;
    @Autowired
    BlogSectionMapper blogSectionMapper;
    @Autowired
    BlogSectionService blogSectionService;

    @Override
    public int save(Blog blog) {
        int result = super.save(blog);
        //如果是带子集的
        if(blog instanceof BlogVo){
            List<BlogSection> blogSectionList = ((BlogVo) blog).getBlogSectionList();
            for (BlogSection blogSection : blogSectionList){
                blogSection.setBlogId(blog.getId());
                blogSectionService.save(blogSection);
            }
        }
        return result;
    }

    @Override
    public boolean existByUrl(String thisHttpPath) {
        Example example = new Example(Blog.class);
        example.createCriteria()
                .andLike("url","%"+thisHttpPath+"%");
        int count = blogMapper.selectCountByExample(example);
        if(count >0){
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteBlog(Integer id) {
        blogMapper.deleteByPrimaryKey(id);
        blogSectionMapper.deleteByPrimaryKey(id);
        blogParagraphMapper.deleteByPrimaryKey(id);
        return true;
    }

    @Override
    public MyBaseMapper<Blog> getBaseMapper() {
        return blogMapper;
    }
}
