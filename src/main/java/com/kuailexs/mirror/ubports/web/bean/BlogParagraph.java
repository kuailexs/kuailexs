package com.kuailexs.mirror.ubports.web.bean;

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
    private Integer googleTranslated = 0;

    @Column(name = "youdao_translated_")
    private Integer youdaoTranslated = 0;


}
