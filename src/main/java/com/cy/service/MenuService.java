package com.cy.service;

import com.cy.entity.Menu;
import com.cy.vo.Node;

import java.util.List;
import java.util.Map;

/**
 * @Author wh
 * @Date 2020/5/27 19:10
 * Description:
 */
public interface MenuService {
    /**
     * 查询菜单
     *
     * @return 菜单集合
     */
    List<Map<String, Object>> findObjects();

    /**
     * 查询菜单
     *
     * @return 菜单集合
     */
    List findObjectsVo();
    /**
     * 删除菜单
     */
    int deleteById(Integer id);
    /**
     * 上级菜单
     * @return 上级菜单
     */
    List<Node> findZtreeMenuNodes();

    int saveObject(Menu entity);

    int updateObject(Menu entity);
}
