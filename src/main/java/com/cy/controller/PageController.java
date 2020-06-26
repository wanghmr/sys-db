package com.cy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author wh
 * @date 2020/5/26
 * Description: 页面跳转
 */
@Controller
@RequestMapping("/")
public class PageController {
    /**
     * 开始页面
     *
     * @return 开始页面
     */
    @RequestMapping("doIndexUI")
    public String page() {
        return "starter";
    }

    /**
     * http://localhost/log/log_list
     * 日志页面
     */
    @RequestMapping("log/log_list")
    public String doLogUi() {
        return "sys/log_list";
    }

    /**
     * 通用页面跳转
     */
    @RequestMapping("*/{moduleName}")
    public String doPageUi(@PathVariable String moduleName) {
        return "sys/" + moduleName;
    }

    /**
     * 分页页面
     *
     * @return 分页页面
     */
    @RequestMapping("doPageUI")
    public String doPageUi() {
        return "common/page";
    }

    /**
     * 登录页面
     *
     * @return 登录页面
     */
    @RequestMapping("doLoginUI")
    public String doLoginUI() {
        return "login";
    }

}
