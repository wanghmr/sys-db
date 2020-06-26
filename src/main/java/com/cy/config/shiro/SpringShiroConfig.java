package com.cy.config.shiro;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

/**
 * @author wh
 * @date 2020/6/21
 * Description:Shiro配置
 * @Configuration 注解描述的类为一个配置对象,
 * 此对象也会交给spring管理
 */
@Configuration
public class SpringShiroConfig {

    /**
     * Shiro框架的核心管理器对象
     *
     * @return 管理器对象
     * @Bean 描述的方法, 其返回值会交给spring管理
     * @Bean 一般应用在整合第三bean资源时
     */
    @Bean
    public DefaultWebSecurityManager newSecurityManager(
            @Autowired Realm realm,
            @Autowired CacheManager cacheManager) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(realm);
        //授权信息缓存
        defaultWebSecurityManager.setCacheManager(cacheManager);
        //记住我
        defaultWebSecurityManager.setRememberMeManager(newRememberMeManager());
        //会话管理
        defaultWebSecurityManager.setSessionManager(newSessionManager());
        return defaultWebSecurityManager;
    }

    /**
     * 配置ShiroFilterFactoryBean对象(通过此对象创建shiro中的过滤器对象)
     */
    @Bean("shiroFilterFactory")
    public ShiroFilterFactoryBean newShiroFilterFactoryBean(@Autowired SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //假如没有认证请求先访问此认证的url
        shiroFilterFactoryBean.setLoginUrl("/doLoginUI");
        //定义map指定请求过滤规则(哪些资源允许匿名访问,哪些必须认证访问)
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        //静态资源允许匿名访问:"anon"
        map.put("/bower_components/**", "anon");
        map.put("/build/**", "anon");
        map.put("/dist/**", "anon");
        map.put("/plugins/**", "anon");
        //登录
        map.put("/user/doLogin", "anon");
        //退出，跳转登录页面
        map.put("/doLogout", "logout");
        //除了匿名访问的资源,其它都要认证("authc")后访问
        map.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }

    /**
     * 配置shiro框架中一些bean对象的生命周期管理器
     */
    @Bean
    public LifecycleBeanPostProcessor newLifecycBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 配置代理对象创建器,通过此对象为目标业务对象创建代理对象
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator newDefaultAdvisorAutoProxyCreator() {
        return new DefaultAdvisorAutoProxyCreator();
    }

    /**
     * 配置advisor对象,shiro框架底层会通过此对象的matchs方法返回值决定是否创建代理对象,进行权限控制
     *
     * @param securityManager 安全管理器
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor newAuthorizationAttributeSourceAdvisor(
            @Autowired SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        return advisor;
    }

    /**
     * 当我们进行授权操作时,每次都会从数据库查询用户权限信息,
     * 为了提高授权性能,可以将用户权限信息查询出来以后进行缓存,
     * 下次授权时从缓存取数据即可.
     *
     * @return 授权缓存
     */
    @Bean
    public CacheManager newCacheManager() {
        return new MemoryConstrainedCacheManager();
    }

    /**
     * 记住我功能
     *
     * @return
     */
    public SimpleCookie newCookie() {
        SimpleCookie c = new SimpleCookie("rememberMe");
        c.setMaxAge(10 * 60);
        return c;
    }

    public CookieRememberMeManager newRememberMeManager() {
        CookieRememberMeManager cManager = new CookieRememberMeManager();
        cManager.setCookie(newCookie());
        return cManager;
    }

    /**
     * 使用shiro框架实现认证操作,用户登录成功会将用户信息写入到会话对象中,其默认时长为30分钟,
     * 假如需要对此进行配置,可参考如下配置:
     *
     * @return 添加会话管理器配置
     */
    public DefaultWebSessionManager newSessionManager() {
        DefaultWebSessionManager sManager = new DefaultWebSessionManager();
        sManager.setGlobalSessionTimeout(60 * 60 * 1000);
        return sManager;
    }


}
