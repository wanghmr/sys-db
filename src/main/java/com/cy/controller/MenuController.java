package com.cy.controller;

import com.cy.entity.Menu;
import com.cy.service.MenuService;
import com.cy.vo.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author wh
 * @date 2020/5/27
 * Description: 菜单controller
 */
@Controller
@RequestMapping("/menu")
public class MenuController {
    private final MenuService menuService;

    @Autowired
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    /**
     * 查询菜单
     * 方案一： Map<String,Object>封装数据（key是属性，value是属性值）
     *
     * @return json
     */
    @RequestMapping("/doFindObjects")
    @ResponseBody
    public JsonResult doFindObjects1() {
        return new JsonResult(menuService.findObjects());
    }

    /**
     * 查询菜单
     * 方案二： MenuParent实体类封装数据
     *
     * @return json
     */
    @RequestMapping("/")
    @ResponseBody
    public JsonResult doFindObjects2() {
        return new JsonResult(menuService.findObjectsVo());
    }

    /**
     * 删除菜单
     * 1)	基于菜单id查询是否有子菜单(有子菜单则不能删除)
     * 2)	根据菜单id删除菜单自身信息.
     * 3)	根据菜单id删除角色菜单关系数据
     *
     * @return json
     */
    @RequestMapping("/doDeleteObject")
    @ResponseBody
    public JsonResult doDeleteObject(Integer id) {
        menuService.deleteById(id);
        return new JsonResult("delete ok");
    }

    /**
     * 上级菜单
     *
     * @return 上级菜单
     */
    @RequestMapping("doFindZtreeMenuNodes")
    @ResponseBody
    public JsonResult doFindZtreeMenuNodes() {
        return new JsonResult(menuService.findZtreeMenuNodes());
    }

    /**
     * 保存
     *
     * @param entity 菜单实体
     * @return 保存成功
     */
    @RequestMapping("doSaveObject")
    @ResponseBody
    public JsonResult doSaveObject(Menu entity) {
        menuService.saveObject(entity);
        return new JsonResult("save ok");
    }

    /**
     * 编辑
     * @param entity 菜单实体
     * @return 编辑成功
     */
    @RequestMapping("doUpdateObject")
    @ResponseBody
    public JsonResult doUpdateObject(Menu entity){
        menuService.updateObject(entity);
        return new JsonResult("update ok");
    }
}
