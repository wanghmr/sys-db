package com.cy.service;

import com.cy.entity.Role;
import com.cy.vo.CheckBox;
import com.cy.vo.PageObject;
import com.cy.vo.SysRoleMenuVo;

import java.util.List;

/**
 * @Author wh
 * @Date 2020/5/27 19:10
 * Description:
 */
public interface RoleService {
    /**
     * 本方法中要分页查询角色信息,并查询角色总记录数据
     *
     * @param pageCurrent 当表要查询的当前页的页码值
     * @return 封装当前实体数据以及分页信息
     */
    PageObject<Role> findPageObjects(
            String name, Integer pageCurrent);

    int deleteObject(Integer id);

    int saveObject(Role entity, Integer[] menuIds);

    SysRoleMenuVo findObjectById(Integer id);

    int updateObject(Role entity, Integer[] menuIds);

    List<CheckBox> findObjects();
}
