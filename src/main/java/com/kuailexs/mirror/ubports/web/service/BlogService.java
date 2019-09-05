package com.kuailexs.mirror.ubports.web.service;

import com.kuailexs.mirror.ubports.web.bean.Blog;
import com.kuailexs.common.service.BaseService;

/**
 * @Author ：dhl
 * @Date ：Created in 14:16 2019/8/23
 * @Description：
 * @Modified By：
 * @Version:
 */
public interface BlogService extends BaseService<Blog,Integer> {

    boolean existByUrl(String thisHttpPath);
}
