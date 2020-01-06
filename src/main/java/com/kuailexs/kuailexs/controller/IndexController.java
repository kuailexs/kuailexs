package com.kuailexs.kuailexs.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;

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
@RequestMapping(value = "/")
public class IndexController {

    /**
     * 首页
     */
//    @RequestMapping(value = {"index.html","/"})
//    public String indexHtml(){
//        return "kuailexs/index";
//    }
}
