package com.kuailexs.mirror.ubports.web.vo;

import com.kuailexs.mirror.ubports.web.bean.BlogParagraph;
import com.kuailexs.mirror.ubports.web.bean.BlogSection;
import lombok.Data;

import java.util.List;

/**
 * @Author ：dhl
 * @Date ：Created in 12:33 2019/9/5
 * @Description：
 * @Modified By：
 * @Version:
 */
@Data
public class BlogSectionVo extends BlogSection {

    List<BlogParagraph> blogParagraphList;

}
