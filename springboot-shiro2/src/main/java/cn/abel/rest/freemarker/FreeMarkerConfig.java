package cn.abel.rest.freemarker;

import javax.annotation.PostConstruct;

import com.jagregory.shiro.freemarker.ShiroTags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

/**
 * freemarker配置
 *
 * @date 2018/08/13 11:43
 */
@Configuration
public class FreeMarkerConfig {

    @Autowired
    freemarker.template.Configuration config;
    @Autowired
    FreeMarkerViewResolver resolver;
    @Autowired
    InternalResourceViewResolver springResolver;

    @PostConstruct
    public void setSharedVariable() {
        config.setNumberFormat("0.##");
        config.setDateFormat("yyyy/MM/dd");
        config.setDateTimeFormat("yyyy-MM-dd HH:mm:ss");

        //自定义视图变量和方法
        resolver.setViewClass(CustomFreeMarkerView.class);

        //shiro标签
        config.setSharedVariable("shiro", new ShiroTags());
    }
}
