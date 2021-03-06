package com.kuailexs.mirror.ubports.web.service;

import com.kuailexs.mirror.ubports.web.bean.BlogParagraph;
import com.kuailexs.common.service.BaseService;

import java.util.List;

/**
 * @Author ：dhl
 * @Date ：Created in 14:16 2019/8/23
 * @Description：
 * @Modified By：
 * @Version:
 */
public interface BlogParagraphService extends BaseService<BlogParagraph,Integer> {

    BlogParagraph getNextGoogleTranslateBlogParagraph();
    BlogParagraph getNextYoudaoTranslateBlogParagraph();


}
