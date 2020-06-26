package com.cy.config.shiro;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

/**
 * @author wh
 * @date 2020/6/21
 * Description: spring过滤器配置
 */
@Configuration
public class WebFilterConfig {
    //取代web.xml中filter配置
    //注册filter对象
    @Bean
    public FilterRegistrationBean newFilterRegistrationBean() {
        //1.构建过滤器的注册器对象
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        //2.注册过滤器对象
        DelegatingFilterProxy shiroFilter = new DelegatingFilterProxy("shiroFilterFactory");
        filterRegistrationBean.setFilter(shiroFilter);
        //3.进行过滤器配置
        //配置过滤器的生命周期管理(可选)由ServletContext对象负责
        //filterRegistrationBean.setEnabled(true);//默认值就是true
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }

}
