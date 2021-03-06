package com.kuailexs.mirror.ubports.web.scheduler;

import com.kuailexs.common.annotation.KuailexsTask;
import com.kuailexs.mirror.ubports.web.bean.Blog;
import com.kuailexs.mirror.ubports.web.bean.BlogParagraph;
import com.kuailexs.mirror.ubports.web.bean.BlogSection;
import com.kuailexs.mirror.ubports.web.service.BlogParagraphService;
import com.kuailexs.mirror.ubports.web.service.BlogSectionService;
import com.kuailexs.mirror.ubports.web.service.BlogService;
import com.kuailexs.translate.TranslateGoogle;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author ：dhl
 * @Date ：Created in 14:53 2019/8/22
 * @Description：
 * @Modified By：
 * @Version:
 */

@Component
public class TranslateUbportsBlogParagraph {

    @Resource
    BlogParagraphService blogParagraphService;

    //间隔 1 分钟
    //@Scheduled(fixedDelay = 60 * 1000)
    @KuailexsTask(fixedDelay = 60 * 1000)
    public void translateBlogParagraph() {
        try {
            BlogParagraph blogParagraph = blogParagraphService.getNextGoogleTranslateBlogParagraph();
            if(blogParagraph != null) {
                String originalText = blogParagraph.getOriginalText();
                BlogParagraph dist = new BlogParagraph();
                dist.setId(blogParagraph.getId());
                //没有字符串也标记为已翻译
                if(StringUtils.hasText(originalText)) {
                    String googleText = TranslateGoogle.translate(originalText, TranslateGoogle.ZH);
                    dist.setGoogleText(googleText);
                }
                dist.setGoogleTranslated(1);
                dist.setLastUpdateTime(new Date());
                blogParagraphService.update(dist);
            }else {
                //没有需要翻译的数据了 等待 12小时
                Thread.sleep(1000 * 60 * 60 * 12);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
