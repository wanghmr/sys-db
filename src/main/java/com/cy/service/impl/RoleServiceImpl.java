package com.cy.service.impl;

import com.cy.common.ValidationException;
import com.cy.entity.Role;
import com.cy.mapper.RoleMapper;
import com.cy.mapper.RoleMenuMapper;
import com.cy.mapper.UserRoleMapper;
import com.cy.service.RoleService;
import com.cy.vo.CheckBox;
import com.cy.vo.PageObject;
import com.cy.vo.SysRoleMenuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author wh
 * @date 2020/5/26
 * Description:日志serviceImpl
 */
@Service
public class RoleServiceImpl implements RoleService {
    private final RoleMapper roleMapper;
    private final RoleMenuMapper roleMenuMapper;
    private final UserRoleMapper userRoleMapper;

    @Autowired
    public RoleServiceImpl(RoleMapper roleMapper, RoleMenuMapper roleMenuMapper, UserRoleMapper userRoleMapper) {
        this.roleMapper = roleMapper;
        this.roleMenuMapper = roleMenuMapper;
        this.userRoleMapper = userRoleMapper;
    }

    @Override
    public PageObject<Role> findPageObjects(
            String name, Integer pageCurrent) {
        //1.验证参数合法性
        //1.1验证pageCurrent的合法性，
        //不合法抛出IllegalArgumentException异常
        if (pageCurrent == null || pageCurrent < 1) {
            throw new IllegalArgumentException("当前页码不正确");
        }
        //2.基于条件查询总记录数
        //2.1) 执行查询
        int rowCount = roleMapper.getRowCount(name);
        //2.2) 验证查询结果，假如结果为0不再执行如下操作
        if (rowCount == 0) {
            throw new ValidationException("记录不存在");
        }
        //3.基于条件查询当前页记录(pageSize定义为2)
        //3.1)定义pageSize
        int pageSize = 2;
        //3.2)计算startIndex
        int startIndex = (pageCurrent - 1) * pageSize;
        //3.3)执行当前数据的查询操作
        List<Role> records = roleMapper.findPageObjects(name, startIndex, pageSize);
        //4.对分页信息以及当前页记录进行封装
        //4.1)构建PageObject对象
        PageObject<Role> pageObject = new PageObject<>();
        //4.2)封装数据
        pageObject.setPageCurrent(pageCurrent);
        pageObject.setPageSize(pageSize);
        pageObject.setRowCount(rowCount);
        pageObject.setRecords(records);
        pageObject.setPageCount((rowCount - 1) / pageSize + 1);
        //5.返回封装结果。
        return pageObject;
    }

    @Override
    public int deleteObject(Integer id) {
        //1.验证参数的合法性
        if (id == null || id < 1) {
            throw new ValidationException("id的值不正确,id=" + id);
        }
        //2.执行dao操作
        roleMenuMapper.deleteObjectsByRoleId(id);
        userRoleMapper.deleteObjectsByRoleId(id);
        int rows = roleMapper.deleteObject(id);
        if (rows == 0) {
            throw new ValidationException("数据可能已经不存在");
        }
        //3.返回结果
        return rows;
    }

    @Override
    public int saveObject(Role entity, Integer[] menuIds) {
        //1.合法性验证
        if (entity == null) {
            throw new ValidationException("保存数据不能为空");
        }
        if (StringUtils.isEmpty(entity.getName())) {
            throw new ValidationException("角色名不能为空");
        }
        if (menuIds == null || menuIds.length == 0) {
            throw new ValidationException("必须为角色赋予权限");
        }
        //2.保存数据
        int rows = roleMapper.insertObject(entity);
        roleMenuMapper.insertObjects(entity.getId(), menuIds);
        //3.返回结果
        return rows;
    }

    @Override
    public SysRoleMenuVo findObjectById(Integer id) {
        //1.合法性验证
        if (id == null || id <= 0) {
            throw new ValidationException("id的值不合法");
        }
        //2.执行查询
        SysRoleMenuVo result = roleMapper.findObjectById(id);
        //3.验证结果并返回
        if (result == null) {
            throw new ValidationException("此记录已经不存在");
        }
        return result;
    }

    @Override
    public int updateObject(Role entity, Integer[] menuIds) {
        //1.合法性验证
        if (entity == null) {
            throw new ValidationException("更新的对象不能为空");
        }
        if (entity.getId() == null) {
            throw new ValidationException("id的值不能为空");
        }

        if (StringUtils.isEmpty(entity.getName())) {
            throw new ValidationException("角色名不能为空");
        }
        if (menuIds == null || menuIds.length == 0) {
            throw new ValidationException("必须为角色指定一个权限");
        }

        //2.更新数据
        int rows = roleMapper.updateObject(entity);
        if (rows == 0) {
            throw new ValidationException("对象可能已经不存在");
        }
        roleMenuMapper.deleteObjectsByRoleId(entity.getId());
        roleMenuMapper.insertObjects(entity.getId(), menuIds);

        //3.返回结果
        return rows;
    }

    @Override
    public List<CheckBox> findObjects() {
        return roleMapper.findObjects();
    }



}
