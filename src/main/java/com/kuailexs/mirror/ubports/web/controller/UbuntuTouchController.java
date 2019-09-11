package com.kuailexs.mirror.ubports.web.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kuailexs.mirror.ubports.web.bean.Blog;
import com.kuailexs.mirror.ubports.web.bean.BlogParagraph;
import com.kuailexs.mirror.ubports.web.bean.BlogSection;
import com.kuailexs.mirror.ubports.web.service.BlogParagraphService;
import com.kuailexs.mirror.ubports.web.service.BlogSectionService;
import com.kuailexs.mirror.ubports.web.service.BlogService;
import com.kuailexs.mirror.ubports.web.vo.BlogSectionVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author ：dhl
 * @Date ：Created in 14:39 2019/8/22
 * @Description：
 * @Modified By：
 * @Version:
 */
@Controller
@RequestMapping(value = "mirror.ubports")
public class UbuntuTouchController {

    @Resource
    BlogService blogService;
    @Resource
    BlogSectionService blogSectionService;
    @Resource
    BlogParagraphService blogParagraphService;

    @RequestMapping(value = "saveQA")
    public void saveQA( String blog_post_name,String blog_title,String blog_content){
        System.out.println(blog_post_name);
        System.out.println(blog_title);
        System.out.println(blog_content);
    }

    @RequestMapping(value = "blog/list")
    public void blogList( Blog blog,Integer pageNum,Integer pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<Blog> list =  blogService.list(blog);
        PageInfo<Blog> page = new PageInfo<Blog>(list);
    }

    @RequestMapping(value = "blog/{id}")
    public String blog(Model model , @PathVariable Integer id){
        Blog blog = blogService.getOne(id);
        BlogSection blogSection = new BlogSection();
        blogSection.setBlogId(id);
        List<BlogSection> list = blogSectionService.list(blogSection);
        List<BlogSection> blogSectionList = new ArrayList<>();
        for(BlogSection blogSection1 : list){
            BlogSectionVo blogSectionVo = new BlogSectionVo();
            BlogParagraph blogParagraph = new BlogParagraph();
            blogParagraph.setSectionId(blogSection1.getId());
            List<BlogParagraph> blogParagraphList = blogParagraphService.list(blogParagraph);
            blogSectionVo.setBlogParagraphList(blogParagraphList);
            blogSectionList.add(blogSectionVo);
        }
        model.addAttribute("blog",blog);
        model.addAttribute("blogSectionList",blogSectionList);
        return "mirror/ubports/blog-details";
    }
}
