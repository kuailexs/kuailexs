package com.kuailexs.mirror.ubports.web.scheduler;

import com.kuailexs.blog.utils.DateKit;
import com.kuailexs.mirror.ubports.web.constant.BlogType;
import com.kuailexs.mirror.ubports.web.bean.Blog;
import com.kuailexs.mirror.ubports.web.bean.BlogParagraph;
import com.kuailexs.mirror.ubports.web.bean.BlogSection;
import com.kuailexs.mirror.ubports.web.service.BlogService;
import com.kuailexs.mirror.ubports.web.bean.view.BlogSectionVo;
import com.kuailexs.mirror.ubports.web.bean.view.BlogVo;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.*;

/**
 * @Author ：dhl
 * @Date ：Created in 14:53 2019/8/22
 * @Description：
 * @Modified By：
 * @Version:
 */
@Component
@Slf4j
public class SyncUbportsBlog {
    @Resource
    BlogService blogService;

    private final String hostPath = "https://ubports.com";

    private static Map<String,String> dateMap = new HashMap<>();

    //间隔 10 小时
    @Scheduled(fixedDelay = 1000 * 60 * 60 * 10)
    public void onTimeSyncUbportsBlog() {
        syncUbportsBlog(BlogType.QA);
        syncUbportsBlog(BlogType.BLOG);
    }



    public void syncUbportsBlog(BlogType blogType) {

        String QAListFirstPagePath = getQAListFirstPagePath(blogType);

        String thisHttpPath = getThisHttpPath(QAListFirstPagePath);

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
                try {
                    String existUrl =  thisHttpPath.substring(thisHttpPath.lastIndexOf("/"),thisHttpPath.lastIndexOf("-")+1);
                    log.info("existUrl：{}",existUrl);
                    boolean exist = blogService.existByUrl(existUrl);
                    if(!exist) {
                        Blog blog = getBlog(thisHttpPath,blogType);
                        blogService.save(blog);
                        log.info("exist：{}",exist);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Blog getBlog(String thisHttpPath , BlogType blogType) throws IOException {
        if(StringUtils.hasLength(thisHttpPath)) {

            //先耍1s再干活
            sleep(1000);
            //Proxy proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("127.0.0.1", 5080));
            //Document document = Jsoup.connect(thisHttpPath).proxy(proxy).get();
            Document document = Jsoup.connect(thisHttpPath).get();

            log.info("Document：{}",document);

            Element element = document.selectFirst("div.blog_title");
            if(element != null){
                return oldBlogStyle(document,thisHttpPath,blogType);
            }else{
                //Element element = document.selectFirst("div.o_wblog_post_title");
                return blogStyleSince202007(document,thisHttpPath,blogType);
            }
        }
        return null;
    }

    private Blog blogStyleSince202007(Document document, String thisHttpPath, BlogType blogType) {
        Element element = document.selectFirst("div.o_wblog_post_title");
        String blog_post_name = element.selectFirst("#o_wblog_post_name").text();
        String subtitle = element.selectFirst("#o_wblog_post_subtitle").text();
        String data = dateMap.get(thisHttpPath.substring(thisHttpPath.lastIndexOf("/")));
        data = DateKit.dateFormat(new Date(data));
        String dataShow = dateMap.get(thisHttpPath.substring(thisHttpPath.lastIndexOf("/")));

        Date thisTime = new Date();

        BlogVo blog = new BlogVo();
        blog.setUrl(thisHttpPath);
        blog.setBlogTitle(blog_post_name);
        blog.setSubtitle(subtitle);
        blog.setDate(data);
        blog.setDateStr(dataShow);
        blog.setType(blogType.getCode());
        blog.setCreateDate(thisTime);

        List<BlogSection> blogSectionList = new ArrayList<>();
        blog.setBlogSectionList(blogSectionList);

        Elements blog_contents = document.selectFirst("div#o_wblog_post_content").select("section");
        for( int i = 0 ; i < blog_contents.size() ; i++ ) {
            //主题
            Element blog_content = blog_contents.get(i);
            BlogSectionVo blogSection = new BlogSectionVo();
            blogSection.setBlogId(blog.getId());
            blogSection.setSort(i+1);

            blogSectionList.add(blogSection);

            List<BlogParagraph> blogParagraphList = new ArrayList<>();
            blogSection.setBlogParagraphList(blogParagraphList);

            List<Node>  nodeList = blog_content.childNodes();
            for ( int j = 0 ; j < nodeList.size() ; j++ ) {
                BlogParagraph blogParagraph = new BlogParagraph();
                Node node = nodeList.get(j);
                if(node instanceof Element){
                    Element element1 = (Element) node;
                    blogParagraph.setOriginalHtml(element1.toString());
                    blogParagraph.setOriginalText(element1.text());
                }else {
                    blogParagraph.setOriginalHtml(node.toString());
                    blogParagraph.setOriginalText(node.toString());
                }
                blogParagraph.setBlogId(blog.getId());
                blogParagraph.setSectionId(blogSection.getId());
                blogParagraph.setCreateTime(thisTime);
                blogParagraph.setSort(j + 1);
                blogParagraph.setGoogleTranslated(0);
                blogParagraph.setYoudaoTranslated(0);
                blogParagraphList.add(blogParagraph);
            }
        }
        return blog;
    }

    /**
     * 2020 - 07  之前
     * @param document
     * @param thisHttpPath
     * @param blogType
     * @return
     */
    private Blog oldBlogStyle(Document document,String thisHttpPath,BlogType blogType) {
        Element element = document.selectFirst("div.blog_title");
        String blog_post_name = element.selectFirst("#blog_post_name").text();
        Element elementH2 = element.selectFirst("h2");
        String subtitle = elementH2.selectFirst("span").text();
        String data = elementH2.selectFirst("div").selectFirst("span").attr("data-oe-original");
        String dataShow = elementH2.selectFirst("div").selectFirst("span").text();

        Date thisTime = new Date();

        BlogVo blog = new BlogVo();
        blog.setUrl(thisHttpPath);
        blog.setBlogTitle(blog_post_name);
        blog.setSubtitle(subtitle);
        blog.setDate(data);
        blog.setDateStr(dataShow);
        blog.setType(blogType.getCode());
        blog.setCreateDate(thisTime);

        List<BlogSection> blogSectionList = new ArrayList<>();
        blog.setBlogSectionList(blogSectionList);

        Elements blog_contents = document.selectFirst("div#blog_content").select("section");
        for( int i = 0 ; i < blog_contents.size() ; i++ ) {
            //主题
            Element blog_content = blog_contents.get(i);
            BlogSectionVo blogSection = new BlogSectionVo();
            blogSection.setBlogId(blog.getId());
            blogSection.setSort(i+1);

            blogSectionList.add(blogSection);

            List<BlogParagraph> blogParagraphList = new ArrayList<>();
            blogSection.setBlogParagraphList(blogParagraphList);

            //段落
//                Elements elements = blog_content.children();
//                for ( int j = 0 ; j < elements.size() ; j++ ) {
//                    Element element1 = elements.get(j);
//                    BlogParagraph blogParagraph = new BlogParagraph();
//                    blogParagraph.setBlogId(blog.getId());
//                    blogParagraph.setSectionId(blogSection.getId());
//                    blogParagraph.setCreateTime(thisTime);
//                    blogParagraph.setSort(j+1);
//                    blogParagraph.setOriginalHtml(element1.toString());
//                    blogParagraph.setOriginalText(element1.text());
//                    blogParagraph.setGoogleTranslated(0);
//                    blogParagraph.setYoudaoTranslated(0);
//                    blogParagraphList.add(blogParagraph);
//                }
            List<Node>  nodeList = blog_content.childNodes();
            for ( int j = 0 ; j < nodeList.size() ; j++ ) {
                BlogParagraph blogParagraph = new BlogParagraph();
                Node node = nodeList.get(j);
                if(node instanceof Element){
                    Element element1 = (Element) node;
                    blogParagraph.setOriginalHtml(element1.toString());
                    blogParagraph.setOriginalText(element1.text());
                }else {
                    blogParagraph.setOriginalHtml(node.toString());
                    blogParagraph.setOriginalText(node.toString());
                }
                blogParagraph.setBlogId(blog.getId());
                blogParagraph.setSectionId(blogSection.getId());
                blogParagraph.setCreateTime(thisTime);
                blogParagraph.setSort(j + 1);
                blogParagraph.setGoogleTranslated(0);
                blogParagraph.setYoudaoTranslated(0);
                blogParagraphList.add(blogParagraph);
            }
        }
        return blog;
    }

    private void getQAListPath(List<String> qaListPath, String thisHttpPath) {
        if(StringUtils.hasLength(thisHttpPath)) {

            //先耍1s再干活
            sleep(1000);

            try {
                Document document = Jsoup.connect(thisHttpPath).get();
                Elements elements = document.select("article.o_wblog_post");
                for(Element elementNext : elements) {
                    String href = elementNext.selectFirst("a").attr("href");
                    if(StringUtils.hasText(href)) {
                        String dateStr = elementNext.selectFirst("time").text();
                        dateMap.put(href.substring(href.lastIndexOf("/")),dateStr);
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

    private String getQAListFirstPagePath(BlogType blogType) {
        String QAListPath = null;
//        if(StringUtils.hasLength(hostPath)) {

            //先耍1s再干活
            sleep(1000);

//            String containsString = "ubuntu-touch-q-a";
            switch (blogType){
                case QA:
//                    containsString = "ubuntu-touch-q-a";
                    QAListPath = "/blog/ubports-blogs-news-1/tag/ubuntu-touch-q-a-3";
                    break;
                case BLOG:
//                    containsString = "blogs";
                    QAListPath = "/blog/ubports-blogs-news-1/tag/blogs-2";
                    break;
            }
//            try {
//                Document document = Jsoup.connect(hostPath).get();
//                Element element = document.selectFirst("#top_menu");
//                Elements elements = element.select("li");
//                for(Element elementNext : elements) {
//                    String href = elementNext.selectFirst("a").attr("href");
//
//                    if(StringUtils.hasText(href) && href.contains(containsString)) {
//                        QAListPath = href;
//                        break;
//                    }
//                }
//            }catch (IOException e){
//                e.printStackTrace();
//            }
//        }
        return QAListPath;
    }
    private String getNextPagePath(String thisHttpPath) {
        String QAListPath = null;
        if(StringUtils.hasLength(thisHttpPath)) {

            //先耍1s再干活
            sleep(1000);

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
    private void sleep(long i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
