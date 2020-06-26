package com.cy.common;

import com.cy.vo.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author wh
 * @date 2020/5/27
 * Description: 全局异常处理类
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 全局异常处理
     *
     * @param e 异常
     * @return 错误页面
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public JsonResult defaultErrorHandler(RuntimeException e) {
        log.error("系统出现错误:", e);
        e.printStackTrace();//也可以写日志
        //封装异常信息
        return new JsonResult(e);

    }

    /**
     * 当我们在执行登录操作时,为了提高用户体验,可对系统中的异常信息进行处理
     *
     * @param e 异常
     * @return 异常的封装结果
     */
    @ExceptionHandler(ShiroException.class)
    @ResponseBody
    public JsonResult doHandleShiroException(ShiroException e) {
        JsonResult r = new JsonResult();
        r.setState(0);
        if (e instanceof UnknownAccountException) {
            r.setMessage("账户不存在");
        } else if (e instanceof LockedAccountException) {
            r.setMessage("账户已被禁用");
        } else if (e instanceof IncorrectCredentialsException) {
            r.setMessage("密码不正确");
        } else if (e instanceof AuthorizationException) {
            r.setMessage("没有此操作权限");
        } else {
            r.setMessage("系统维护中");
        }
        e.printStackTrace();
        return r;
    }


}
