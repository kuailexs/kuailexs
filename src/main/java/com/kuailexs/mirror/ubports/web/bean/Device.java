package com.kuailexs.mirror.ubports.web.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
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
