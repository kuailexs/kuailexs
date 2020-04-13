package com.kuailexs.kuailexs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
    @RequestMapping(value = {"index.html","/"})
    public String indexHtml(){
        //return "kuailexs/index";
        //return "mirror/ubports/index";//默认跳ubports博客翻译
        return "redirect:mirror.ubports/blog/listPage";//默认跳ubports博客翻译
    }
    /**
     * 关于
     */
    @RequestMapping(value = {"about.html","about"})
    public String about(){
        return "about";
    }
}
