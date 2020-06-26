package com.cy.mapper;

import com.cy.entity.Menu;
import com.cy.vo.MenuParent;
import com.cy.vo.Node;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author wh
 * @date 2020/5/27
 * Description: 菜单mapper
 */
@Mapper
public interface MenuMapper {

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
    List<MenuParent> findObjectsVo();

    /**
     * 根据菜单id统计子菜单的个数
     *
     * @param id id
     * @return 根据菜单id统计子菜单的个数
     */
    int getChildCount(Integer id);

    /**
     * 根据id 删除菜单
     *
     * @param id id
     * @return 删除菜单的数量
     */
    int deleteObject(Integer id);

    /**
     * 菜单新增上级菜单
     *
     * @return 菜单新增上级菜单
     */
    List<Node> findZtreeMenuNodes();

    int insertObject(Menu entity);

    int updateObject(Menu entity);

    List<String> findPermissions(@Param("menuIds") Integer[] menuIds);

}
