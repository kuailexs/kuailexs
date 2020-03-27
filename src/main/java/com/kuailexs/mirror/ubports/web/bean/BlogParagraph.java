package com.kuailexs.mirror.ubports.web.bean;

import com.kuailexs.common.annotation.TableSql;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @Author ：dhl
 * @Date ：Created in 14:26 2019/8/23
 * @Description：
 * @Modified By：
 * @Version:
 */
@Data
@Table(name = "mirror_ubports_blog_paragraph")
@TableSql("CREATE TABLE `mirror_ubports_blog_paragraph` (\n" +
        "  `id_` int(11) NOT NULL AUTO_INCREMENT,\n" +
        "  `blog_id_` int(11) DEFAULT NULL,\n" +
        "  `section_id_` int(11) DEFAULT NULL,\n" +
        "  `original_html_` text,\n" +
        "  `original_text_` text,\n" +
        "  `google_text_` text,\n" +
        "  `google_translated_` int(11) DEFAULT NULL,\n" +
        "  `youdao_text_` text,\n" +
        "  `youdao_translated_` int(11) DEFAULT NULL,\n" +
        "  `review_text_` text,\n" +
        "  `review_html_` text,\n" +
        "  `create_time_` datetime DEFAULT NULL,\n" +
        "  `last_update_time_` datetime DEFAULT NULL,\n" +
        "  `sort_` int(11) DEFAULT NULL,\n" +
        "  PRIMARY KEY (`id_`),\n" +
        "  KEY `blog_id_` (`blog_id_`) USING BTREE,\n" +
        "  KEY `google_translated_` (`google_translated_`),\n" +
        "  KEY `section_id_` (`section_id_`),\n" +
        "  KEY `youdao_translated_` (`youdao_translated_`)\n" +
        ")")
public class BlogParagraph {
    @Id
    @Column(name = "id_")
    private Integer id;

    @Column(name = "sort_")
    private Integer sort;

    @Column(name = "blog_id_")
    private Integer blogId;

    @Column(name = "section_id_")
    private Integer sectionId;

    @Column(name = "create_time_")
    private Date createTime;

    @Column(name = "last_update_time_")
    private Date lastUpdateTime;

    @Column(name = "original_html_")
    private String originalHtml;

    @Column(name = "original_text_")
    private String originalText;

    @Column(name = "google_text_")
    private String googleText;

    @Column(name = "youdao_text_")
    private String youdaoText;

    @Column(name = "review_text_")
    private String reviewText;

    @Column(name = "review_html_")
    private String reviewHtml;

    @Column(name = "google_translated_")
    private Integer googleTranslated ;

    @Column(name = "youdao_translated_")
    private Integer youdaoTranslated ;


}
