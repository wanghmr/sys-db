package com.cy.controller;

import com.cy.entity.Role;
import com.cy.service.RoleService;
import com.cy.vo.JsonResult;
import com.cy.vo.PageObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author wh
 * @date 2020/5/26
 * Description: 日志controller
 */
@Controller
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    /**
     * 分页查询
     *
     * @param name
     * @param pageCurrent
     * @return
     */
    @RequestMapping("doFindPageObjects")
    @ResponseBody
    public JsonResult doFindPageObjects(String name, Integer pageCurrent) {
        PageObject<Role> pageObject = roleService.findPageObjects(name, pageCurrent);
        return new JsonResult(pageObject);
    }

    /**
     * 删除
     * 1）	根据id删除角色自身信息（sys_roles）
     * 2）	根据id删除角色与菜单的关系数据(sys_role_menus)
     * 3）	根据id删除角色与用户的关系数据(sys_user_roles)
     */
    @RequestMapping("doDeleteObject")
    @ResponseBody
    public JsonResult doDeleteObject(Integer id) {
        roleService.deleteObject(id);
        return new JsonResult("delete Ok");
    }

    /**
     * 新增
     *
     * @param entity
     * @param menuIds
     * @return
     */
    @RequestMapping("doSaveObject")
    @ResponseBody
    public JsonResult doSaveObject(Role entity, Integer[] menuIds) {
        roleService.saveObject(entity, menuIds);
        return new JsonResult("save ok");
    }

    /**
     * 编辑回显数据
     * @param id
     * @return
     */
    @RequestMapping("doFindObjectById")
    @ResponseBody
    public JsonResult doFindObjectById(Integer id) {
        return new JsonResult(roleService.findObjectById(id));
    }

    /**
     * 编辑
     * @param entity
     * @param menuIds
     * @return
     */
    @RequestMapping("doUpdateObject")
    @ResponseBody
    public JsonResult doUpdateObject(Role entity, Integer[] menuIds) {
        roleService.updateObject(entity, menuIds);
        return new JsonResult("update ok");
    }

    @RequestMapping("doFindRoles")
    @ResponseBody
    public JsonResult doFindObjects(){
        return new JsonResult(roleService.findObjects());
    }

}