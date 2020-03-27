package com.kuailexs.mirror.ubports.web.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kuailexs.common.annotation.TableSql;
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
@TableSql("CREATE TABLE `mirror_ubports_blog` (\n" +
        "  `id_` int(11) NOT NULL AUTO_INCREMENT,\n" +
        "  `blog_title_` varchar(512) DEFAULT NULL,\n" +
        "  `subtitle_` varchar(200) DEFAULT NULL,\n" +
        "  `date_` varchar(100) DEFAULT NULL,\n" +
        "  `date_str_` varchar(100) DEFAULT NULL,\n" +
        "  `create_date_` datetime DEFAULT NULL,\n" +
        "  `blog_title_zh_` varchar(512) DEFAULT NULL,\n" +
        "  `subtitle_zh_` varchar(200) DEFAULT NULL,\n" +
        "  `type_` int(11) DEFAULT NULL,\n" +
        "  `url_` varchar(512) DEFAULT NULL,\n" +
        "  PRIMARY KEY (`id_`)\n" +
        ") ")
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

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @Column(name = "create_date_")
    private Date createDate;

    @Column(name = "blog_title_zh_")
    private String blogTitleZh;

    @Column(name = "subtitle_zh_")
    private String subtitleZh;

    /**
     * 1.问答  2.博客
     */
    @Column(name = "type_")
    private Integer type;
}
