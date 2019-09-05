package com.kuailexs.mirror.ubports.web.bean;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @Author ：dhl
 * @Date ：Created in 14:20 2019/8/23
 * @Description：
 * @Modified By：
 * @Version:
 */
@Data
@Table(name = "mirror_ubports_blog")
public class Blog {

    @Id
    @Column(name = "id_")
    private Integer id;

    @Column(name = "url_")
    private String url;

    @Column(name = "blog_title_")
    private String blogTitle;

    @Column(name = "subtitle_")
    private String subtitle;

    @Column(name = "date_")
    private String date;

    @Column(name = "date_str_")
    private String dateStr;

    @Column(name = "create_date_")
    private Date createDate;

    @Column(name = "blog_title_zh_")
    private String blogTitleZh;

    @Column(name = "subtitle_zh_")
    private String subtitleZh;

    /**
     * 1.博客  2.问答
     */
    @Column(name = "type_")
    private Integer type;
}
