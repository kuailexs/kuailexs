package com.kuailexs.common.bean;

import com.kuailexs.common.annotation.TableSql;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author ：dhl
 * @Date ：Created in 14:02 2020/3/27
 * @Description：
 * @Modified By：
 * @Version:
 */
@Data
@Table(name = "kuailexs_user")
@TableSql("CREATE TABLE `kuailexs_user` (\n" +
        "  `id_` int(11) NOT NULL AUTO_INCREMENT,\n" +
        "  `account_` varchar(50) DEFAULT NULL,\n" +
        "  `name_` varchar(100) DEFAULT NULL,\n" +
        "  `password_` varchar(40) DEFAULT NULL,\n" +
        "  `admin_` int(11) DEFAULT '0',\n" +
        "  PRIMARY KEY (`id_`),\n" +
        "  KEY `idx_login` (`account_`,`password_`)\n" +
        ") ")
public class User {
    @Id
    @Column(name = "id_")
    private Integer id;

    @Column(name = "account_")
    private String account;

    @Column(name = "name_")
    private String name;

    @Column(name = "password_")
    private String password;

    @Column(name = "admin_")
    private Integer admin;

}
