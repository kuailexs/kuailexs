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
@Table(name = "mirror_ubports_device")
@TableSql("CREATE TABLE `mirror_ubports_device` (\n" +
        "  `id_` int(11) NOT NULL AUTO_INCREMENT,\n" +
        "  `device_name_` varchar(200) DEFAULT NULL COMMENT '设备名',\n" +
        "  `source_remark_` varchar(1024) DEFAULT NULL,\n" +
        "  `download_remark_` varchar(1024) DEFAULT NULL,\n" +
        "  `remark_` text,\n" +
        "  `create_date_` datetime DEFAULT NULL,\n" +
        "  PRIMARY KEY (`id_`)\n" +
        ") ")
public class Device {

    @Id
    @Column(name = "id_")
    private Integer id;

    @Column(name = "device_name_")
    private String deviceName;

    @Column(name = "source_remark_")
    private String sourceRemark;

    @Column(name = "download_remark_")
    private String downloadRemark;

    @Column(name = "remark_")
    private String remark;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @Column(name = "create_date_")
    private Date createDate;

}
