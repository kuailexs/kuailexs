package com.kuailexs.common.config;


import com.kuailexs.common.tools.TaleUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 向mvc中添加自定义组件
 * Created by BlueT on 2017/3/9.
 */
@Component
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    /**
     * 添加静态资源文件，外部可以直接访问地址
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/**").addResourceLocations("file:"+ TaleUtils.getUplodFilePath()+"upload/");
        super.addResourceHandlers(registry);
    }
}
