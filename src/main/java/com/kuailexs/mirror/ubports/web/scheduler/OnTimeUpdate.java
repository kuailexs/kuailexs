package com.kuailexs.mirror.ubports.web.scheduler;

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
import org.junit.Test;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
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
@EnableScheduling
@Component
public class OnTimeUpdate {
    @Resource
    BlogService blogService;
    @Resource
    BlogSectionService blogSectionService;
    @Resource
    BlogParagraphService blogParagraphService;

    private final String hostPath = "https://ubports.com";

    @Scheduled(cron = "0/30 * * * * ?")
    public void OnTimeUpdate_() {


        String thisHttpPath = "https://ubports.com";

        String QAListFirstPagePath = getQAListFirstPagePath(thisHttpPath);

        thisHttpPath = getThisHttpPath(QAListFirstPagePath);

        List<String> QAListPath = new ArrayList<>();
        if(StringUtils.hasText(thisHttpPath)){
            do{
                getQAListPath(QAListPath,thisHttpPath);
                String nextPagePath = getNextPagePath(thisHttpPath);
                thisHttpPath = getThisHttpPath(nextPagePath);
            }while (StringUtils.hasText(thisHttpPath));
        }
        if(QAListPath.size() > 0){
            for (String QAPath : QAListPath){
                thisHttpPath = getThisHttpPath(QAPath);
                getBlog(thisHttpPath);
            }
        }
    }

    private void getBlog(String thisHttpPath) {
        if(StringUtils.hasLength(thisHttpPath)) {
            TranslateGoogle translateGoogle= new TranslateGoogle();
            try {
                Document document = Jsoup.connect(thisHttpPath).get();
                Element element = document.selectFirst("div.blog_title");
                String blog_post_name = element.selectFirst("#blog_post_name").text();
                Element elementH2 = element.selectFirst("h2");
                String subtitle = elementH2.selectFirst("span").text();
                String data = elementH2.selectFirst("div").selectFirst("span").attr("data-oe-original");
                String dataShow = elementH2.selectFirst("div").selectFirst("span").text();

                Date thisTime = new Date();

                Blog blog = new Blog();
                blog.setBlogTitle(blog_post_name);
                blog.setSubtitle(subtitle);
                blog.setDate(data);
                blog.setDateStr(dataShow);
                blog.setType(1);
                blog.setCreateDate(thisTime);

                blogService.save(blog);

                Elements blog_contents = document.selectFirst("div#blog_content").select("section");
                for( int i = 0 ; i < blog_contents.size() ; i++ ) {
                    Element blog_content = blog_contents.get(i);

                    BlogSection blogSection = new BlogSection();
                    blogSection.setBlogId(blog.getId());
                    blogSection.setSort(i+1);

                    blogSectionService.save(blogSection);

                    Elements elements = blog_content.children();
                    for ( int j = 0 ; j < elements.size() ; j++ ) {
                        Element element1 = elements.get(j);

                        BlogParagraph blogParagraph = new BlogParagraph();
                        blogParagraph.setBlogId(blog.getId());
                        blogParagraph.setSectionId(blogSection.getId());
                        blogParagraph.setCreateTime(thisTime);
                        blogParagraph.setSort(j+1);
                        blogParagraph.setOriginalHtml(element1.toString());
                        blogParagraph.setOriginalText(element1.text());
                        blogParagraphService.save(blogParagraph);

                    }
                }
            }catch (IOException e){
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void getQAListPath(List<String> qaListPath, String thisHttpPath) {
        if(StringUtils.hasLength(thisHttpPath)) {
            try {
                Document document = Jsoup.connect(thisHttpPath).get();
                Elements elements = document.select("div.card");
                for(Element elementNext : elements) {
                    String href = elementNext.selectFirst("a").attr("href");
                    if(StringUtils.hasText(href)) {
                        qaListPath.add(href);
                    }
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private String getThisHttpPath(String path) {
        String thisHttpPath = null;
        if(StringUtils.hasText(path)){
            if(!path.startsWith("http")){
                if(path.startsWith("/")){
                    thisHttpPath = hostPath + path;
                }else{
                    thisHttpPath = thisHttpPath + path;
                }
            }
        }
        return thisHttpPath;
    }

    private String getQAListFirstPagePath(String thisHttpPath) {
        String QAListPath = null;
        if(StringUtils.hasLength(thisHttpPath)) {
            try {
                Document document = Jsoup.connect(thisHttpPath).get();
                Element element = document.selectFirst("#top_menu");
                Elements elements = element.select("li");
                for(Element elementNext : elements) {
                    String href = elementNext.selectFirst("a").attr("href");
                    if(StringUtils.hasText(href) && href.contains("ubuntu-touch-q-a")) {
                        QAListPath = href;
                        break;
                    }
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return QAListPath;
    }
    private String getNextPagePath(String thisHttpPath) {
        String QAListPath = null;
        if(StringUtils.hasLength(thisHttpPath)) {
            try {
                Document document = Jsoup.connect(thisHttpPath).get();
                Element element = document.selectFirst("ul.pagination");
                Element elementNext = element.select("li").last();
                QAListPath = elementNext.selectFirst("a").attr("href");
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return QAListPath;
    }
}
