package com.kuailexs.mirror.ubports.web.bean;

import com.kuailexs.common.annotation.TableSql;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author ：dhl
 * @Date ：Created in 14:26 2019/8/23
 * @Description：
 * @Modified By：
 * @Version:
 */
@Data
@Table(name = "mirror_ubports_blog_section")
@TableSql("CREATE TABLE `mirror_ubports_blog_section` (\n" +
        "  `id_` int(11) NOT NULL AUTO_INCREMENT,\n" +
        "  `sort_` int(11) NOT NULL,\n" +
        "  `blog_id_` int(11) NOT NULL,\n" +
        "  PRIMARY KEY (`id_`),\n" +
        "  KEY `blog_id_` (`blog_id_`)\n" +
        ") ")
public class BlogSection {
    @Id
    @Column(name = "id_")
    private Integer id;

    @Column(name = "sort_")
    private Integer sort;

    @Column(name = "blog_id_")
    private Integer blogId;
}
