package com.cy.controller;

import com.cy.entity.Dept;
import com.cy.service.DeptService;
import com.cy.vo.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wh
 * @date 2020/5/27
 * Description: 菜单controller
 */
@RestController
@RequestMapping("/dept/")
public class DeptController {
    @Autowired
    private DeptService sysDeptService;

    /**
     * 查询
     *
     * @return
     */
    @RequestMapping("doFindObjects")
    public JsonResult doFindObjects() {
        return new JsonResult(sysDeptService.findObjects());
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @RequestMapping("doDeleteObject")
    @ResponseBody
    public JsonResult doDeleteObject(Integer id) {
        sysDeptService.deleteObject(id);
        return new JsonResult("delete ok");
    }

    /**
     * 上级部门（树）
     *
     * @return
     */
    @RequestMapping("doFindZTreeNodes")
    public JsonResult doFindZTreeNodes() {
        return new JsonResult(sysDeptService.findZTreeNodes());
    }

    /**
     * 编辑
     *
     * @param entity
     * @return
     */
    @RequestMapping("doUpdateObject")
    public JsonResult doUpdateObject(Dept entity) {
        sysDeptService.updateObject(entity);
        return new JsonResult("update ok");
    }

    /**
     * 新增
     *
     * @param entity
     * @return
     */
    @RequestMapping("doSaveObject")
    public JsonResult doSaveObject(Dept entity) {
        sysDeptService.saveObject(entity);
        return new JsonResult("save ok");
    }

}
