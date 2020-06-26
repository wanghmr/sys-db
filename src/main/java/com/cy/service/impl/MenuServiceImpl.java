package com.cy.service.impl;

import com.cy.common.ValidationException;
import com.cy.entity.Menu;
import com.cy.mapper.MenuMapper;
import com.cy.mapper.RoleMenuMapper;
import com.cy.service.MenuService;
import com.cy.vo.MenuParent;
import com.cy.vo.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @author wh
 * @date 2020/5/27
 * Description: 菜单service
 */
@Service
public class MenuServiceImpl implements MenuService {
    private final MenuMapper menuMapper;
    private final RoleMenuMapper roleMenuMapper;

    @Autowired
    public MenuServiceImpl(MenuMapper menuMapper, RoleMenuMapper roleMenuMapper) {
        this.menuMapper = menuMapper;
        this.roleMenuMapper = roleMenuMapper;
    }

    /**
     * 查询菜单
     *
     * @return 菜单集合
     */
    @Override
    public List<Map<String, Object>> findObjects() {
        List<Map<String, Object>> list = menuMapper.findObjects();
        if (list == null || list.size() == 0) {
            throw new ValidationException("没有对应的菜单信息");
        }
        return list;
    }

    /**
     * 查询菜单
     *
     * @return 菜单集合
     */
    @Override
    public List findObjectsVo() {
        List<MenuParent> list = menuMapper.findObjectsVo();
        if (list == null || list.size() == 0) {
            throw new ValidationException("没有对应的菜单信息");
        }
        return list;
    }

    /**
     * 删除菜单
     */
    @Override
    public int deleteById(Integer id) {
        //1.验证数据的合法性
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("请先选择");
        }
        //2.基于id进行子元素查询
        int count = menuMapper.getChildCount(id);
        if (count > 0) {
            throw new ValidationException("请先删除子菜单");
        }
        //3.删除菜单元素
        int rows = menuMapper.deleteObject(id);
        if (rows == 0) {
            throw new ValidationException("此菜单可能已经不存在");
        }
        //4.删除角色,菜单关系数据
        roleMenuMapper.deleteObjectsByMenuId(id);
        //5.返回结果
        return rows;
    }

    /**
     * 上级菜单
     *
     * @return 上级菜单
     */
    @Override
    public List<Node> findZtreeMenuNodes() {
        return menuMapper.findZtreeMenuNodes();
    }

    @Override
    public int saveObject(Menu entity) {
        //1.合法验证
        if (entity == null) {
            throw new ValidationException("保存对象不能为空");
        }
        if (StringUtils.isEmpty(entity.getName())) {
            throw new ValidationException("菜单名不能为空");
        }
        int rows;
        //2.保存数据
        try {
            rows = menuMapper.insertObject(entity);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ValidationException("保存失败");
        }
        //3.返回数据
        return rows;
    }

    @Override
    public int updateObject(Menu entity) {
        //1.合法验证
        if(entity==null) {
            throw new ValidationException("保存对象不能为空");
        }
        if(StringUtils.isEmpty(entity.getName())) {
            throw new ValidationException("菜单名不能为空");
        }
        //2.更新数据
        int rows=menuMapper.updateObject(entity);
        if(rows==0) {
            throw new ValidationException("记录可能已经不存在");
        }
        //3.返回数据
        return rows;
    }


}
