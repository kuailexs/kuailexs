package com.kuailexs.iptv.bean;

import com.kuailexs.common.annotation.TableSql;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "iptv_one")
@TableSql("CREATE TABLE `iptv_one` (\n" +
        "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
        "  `info` varchar(1024) DEFAULT NULL,\n" +
        "  `url` varchar(1024) DEFAULT NULL,\n" +
        "  `type` int(11) DEFAULT NULL,\n" +
        "  `status` int(11) DEFAULT NULL,\n" +
        "  `score` int(11) DEFAULT NULL,\n" +
        "  `status_desc` text,\n" +
        "  PRIMARY KEY (`id`)\n" +
        ") ")
public class IptvOne {

    @Id
    Integer id;
    String info;
    String url;
    Integer type;// 1,http 2,其它
    Integer status;// -1 无效 , 0,待检查，1有效，2.无法检查
    Integer score;// 得分 0-100 不好-好
    @Column(name = "status_desc")
    String statusDesc;// 描述

    public IptvOne(String url) {
        this.url = url;
    }
}
