package com.kuailexs.mirror.ubports.web.vo;

import com.kuailexs.mirror.ubports.web.bean.Blog;
import com.kuailexs.mirror.ubports.web.bean.BlogSection;
import lombok.Data;

import java.util.List;

/**
 * @Author ：dhl
 * @Date ：Created in 12:31 2019/9/5
 * @Description：
 * @Modified By：
 * @Version:
 */
@Data
public class BlogVo extends Blog {

    List<BlogSection> blogSectionList;

}
