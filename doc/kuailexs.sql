
-- ----------------------------
-- Table structure for mirror_ubports_blog
-- ----------------------------
DROP TABLE IF EXISTS `mirror_ubports_blog`;
CREATE TABLE `mirror_ubports_blog` (
  `id_` int(11) NOT NULL AUTO_INCREMENT,
  `blog_title_` varchar(512) DEFAULT NULL,
  `subtitle_` varchar(200) DEFAULT NULL,
  `date_` varchar(100) DEFAULT NULL,
  `date_str_` varchar(100) DEFAULT NULL,
  `create_date_` datetime DEFAULT NULL,
  `blog_title_zh_` varchar(512) DEFAULT NULL,
  `subtitle_zh_` varchar(200) DEFAULT NULL,
  `type_` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_`)
) ;

-- ----------------------------
-- Table structure for mirror_ubports_blog_paragraph
-- ----------------------------
DROP TABLE IF EXISTS `mirror_ubports_blog_paragraph`;
CREATE TABLE `mirror_ubports_blog_paragraph` (
  `id_` int(11) NOT NULL AUTO_INCREMENT,
  `blog_id_` int(11) DEFAULT NULL,
  `section_id_` int(11) DEFAULT NULL,
  `original_html_` text,
  `original_text_` text,
  `google_text_` text,
  `youdao_text_` text,
  `review_text_` text,
  `review_html_` text,
  `create_time_` datetime DEFAULT NULL,
  `last_update_time_` datetime DEFAULT NULL,
  `sort_` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_`)
) ;

-- ----------------------------
-- Table structure for mirror_ubports_blog_section
-- ----------------------------
DROP TABLE IF EXISTS `mirror_ubports_blog_section`;
CREATE TABLE `mirror_ubports_blog_section` (
  `id_` int(11) NOT NULL AUTO_INCREMENT,
  `sort_` int(11) NOT NULL,
  `blog_id_` int(11) NOT NULL,
  PRIMARY KEY (`id_`)
) ;
