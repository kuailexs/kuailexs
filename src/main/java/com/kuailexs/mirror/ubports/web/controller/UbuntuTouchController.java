package com.kuailexs.mirror.ubports.web.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kuailexs.mirror.ubports.web.bean.Blog;
import com.kuailexs.mirror.ubports.web.bean.BlogParagraph;
import com.kuailexs.mirror.ubports.web.bean.BlogSection;
import com.kuailexs.mirror.ubports.web.bean.Device;
import com.kuailexs.mirror.ubports.web.bean.view.ResultPage;
import com.kuailexs.mirror.ubports.web.service.BlogParagraphService;
import com.kuailexs.mirror.ubports.web.service.BlogSectionService;
import com.kuailexs.mirror.ubports.web.service.BlogService;
import com.kuailexs.mirror.ubports.web.bean.view.BlogSectionVo;
import com.kuailexs.mirror.ubports.web.service.DeviceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    @Resource
    DeviceService deviceService;

    /**
     * 列表页面
     * @param model
     * @param type
     * @return
     */
    @RequestMapping(value = "blog/listPage")
    public String blogPage(Model model,Integer type){
        model.addAttribute("type",type);
        return "mirror/ubports/index";
    }

    /**
     * 内容列表
     * @param model
     * @param blog
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping(value = "blog/list")
    @ResponseBody
    public ResultPage<Blog> blogList(Model model , Blog blog, Integer page, Integer limit){
        if(page == null || page < 1){
            page =1;
        }
        if(limit == null || limit < 1){
            limit =12;
        }
        PageHelper.startPage(page, limit,"date_ desc");
        List<Blog> list =  blogService.list(blog);
        PageInfo<Blog> pageInfo = new PageInfo<>(list);
        //返回数据
        ResultPage<Blog> resultPage = new ResultPage<>();
        resultPage.setCode(0);
        resultPage.setMsg("成功");
        resultPage.setData(pageInfo.getList());
        resultPage.setCount(pageInfo.getTotal());
        return resultPage;
    }

    /**
     * 详情展示页
     * @param model
     * @param id
     * @return
     */
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
