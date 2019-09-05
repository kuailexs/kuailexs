package com.kuailexs.mirror.ubports.web.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author ：dhl
 * @Date ：Created in 14:39 2019/8/22
 * @Description：
 * @Modified By：
 * @Version:
 */
@RestController
@RequestMapping(value = "mirror.ubports")
public class UbuntuTouchController {

    @RequestMapping(value = "saveQA")
    public void saveQA( String blog_post_name,String blog_title,String blog_content){
        System.out.println(blog_post_name);
        System.out.println(blog_title);
        System.out.println(blog_content);
    }
}
