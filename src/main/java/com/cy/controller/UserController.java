package com.cy.controller;

import com.cy.entity.User;
import com.cy.service.UserService;
import com.cy.vo.JsonResult;
import com.cy.vo.PageObject;
import com.cy.vo.UserDeptVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @author wh
 * @date 2020/6/16
 * Description: 用户controller
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    /**
     * 分页查询
     * 方案一：比较复杂（自己写）
     *
     * @param username    用户名
     * @param pageCurrent 当前页码值
     */
    @RequestMapping("/")
    @ResponseBody
    public JsonResult findObjeectsPage(String username, Integer pageCurrent) {
        return new JsonResult(userService.findObjectsPage(username, pageCurrent));
    }

    /**
     * 分页查询
     * 方案一：比较复杂（自己写）
     *
     * @param username    用户名
     * @param pageCurrent 当前页码值
     */
    @RequestMapping("/doFindPageObjects")
    @ResponseBody
    public JsonResult doFindPageObjects(
            String username, Integer pageCurrent) {
        PageObject<UserDeptVo> pageObject = userService.findPageObjects(username, pageCurrent);
        return new JsonResult(pageObject);
    }

    /**
     * 启用/禁用
     *
     * @param id    id
     * @param valid 状态
     */
    @RequestMapping("doValidById")
    @ResponseBody
    public JsonResult doValidById(Integer id, Integer valid) {
        //"admin"用户将来是登陆用户
        userService.validById(id, valid, "admin");
        return new JsonResult("update ok");
    }

    /**
     * 用户新增
     *
     * @param entity  用户实体
     * @param roleIds 角色ids
     * @return 新增成功
     */
    @RequestMapping("doSaveObject")
    @ResponseBody
    public JsonResult doSaveObject(User entity, Integer[] roleIds) {
        userService.saveObject(entity, roleIds);
        return new JsonResult("save ok");
    }

    /**
     * 编辑数据回显
     *
     * @param id id
     * @return 回显数据
     */
    @RequestMapping("doFindObjectById")
    @ResponseBody
    public JsonResult doFindObjectById(Integer id) {
        Map<String, Object> map = userService.findObjectById(id);
        return new JsonResult(map);
    }

    /**
     * 编辑
     *
     * @param entity  用户实体
     * @param roleIds 角色ids
     * @return 编辑成功
     */
    @RequestMapping("doUpdateObject")
    @ResponseBody
    public JsonResult doUpdateObject(User entity, Integer[] roleIds) {
        userService.updateObject(entity, roleIds);
        return new JsonResult("update ok");
    }

    /**
     * 登录
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @RequestMapping("doLogin")
    @ResponseBody
    public JsonResult doLogin(String username,String password, boolean isRememberMe){
        //1.获取Subject对象
        Subject subject= SecurityUtils.getSubject();
        //2.通过Subject提交用户信息,交给shiro框架进行认证操作
        //2.1对用户进行封装
        UsernamePasswordToken token=new UsernamePasswordToken(username,password);
        if(isRememberMe) {
            token.setRememberMe(true);
        }

        //2.2对用户信息进行身份认证
        subject.login(token);
        //分析:
        //1)token会传给shiro的SecurityManager
        //2)SecurityManager将token传递给认证管理器
        //3)认证管理器会将token传递给realm
        return new JsonResult("login ok");
    }

    /**
     * 修改密码
     * 路径：user/doUpdatePassword
     */
    @RequestMapping("/doUpdatePassword")
    @ResponseBody
    public JsonResult doUpdatePassword(String pwd, String newPwd, String cfgPwd) {
        userService.doUpdatePassword(pwd, newPwd, cfgPwd);
        return new JsonResult("updatePassword ok");
    }
}
