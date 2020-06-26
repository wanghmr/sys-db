package com.cy.service.impl;

import com.cy.common.ValidationException;
import com.cy.entity.Dept;
import com.cy.mapper.DeptMapper;
import com.cy.mapper.UserMapper;
import com.cy.service.DeptService;
import com.cy.vo.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @author wh
 * @date 2020/6/15
 * Description:
 */
@Service
public class DeptServiceImpl implements DeptService {
    @Autowired
    private DeptMapper sysDeptDao;
    @Autowired
    private UserMapper sysUserDao;


    @Override
    public List<Map<String, Object>> findObjects() {
        List<Map<String, Object>> list = sysDeptDao.findObjects();
        if (list == null || list.size() == 0) {
            throw new ValidationException("没有部门信息");
        }
        return list;
    }

    @Override
    public int deleteObject(Integer id) {
        //1.合法性验证
        if (id == null || id <= 0) {
            throw new ValidationException("数据不合法,id=" + id);
        }
        //2.执行删除操作
        //2.1判定此id对应的菜单是否有子元素
        int childCount = sysDeptDao.getChildCount(id);
        if (childCount > 0) {
            throw new ValidationException("此元素有子元素，不允许删除");
        }
        //2.2判定此部门是否有用户
        int userCount = sysUserDao.getUserCountByDeptId(id);
        if (userCount > 0) {
            throw new ValidationException("此部门有员工，不允许对部门进行删除");
        }
        //2.2判定此部门是否已经被用户使用,假如有则拒绝删除
        //2.3执行删除操作
        int rows = sysDeptDao.deleteObject(id);
        if (rows == 0) {
            throw new ValidationException("此信息可能已经不存在");
        }
        return rows;
    }

    @Override
    public List<Node> findZTreeNodes() {
        List<Node> list = sysDeptDao.findZTreeNodes();
        if (list == null || list.size() == 0) {
            throw new ValidationException("没有部门信息");
        }
        return list;
    }

    @Override
    public int updateObject(Dept entity) {
        //1.合法验证
        if (entity == null) {
            throw new ValidationException("保存对象不能为空");
        }
        if (StringUtils.isEmpty(entity.getName())) {
            throw new ValidationException("部门不能为空");
        }
        int rows;
        //2.更新数据
        try {
            rows = sysDeptDao.updateObject(entity);
        } catch (Exception e) {
            e.printStackTrace();
            {
                throw new ValidationException("更新失败");
            }
        }
        //3.返回数据
        return rows;
    }

    @Override
    public int saveObject(Dept entity) {
        //1.合法验证
        if (entity == null) {
            throw new ValidationException("保存对象不能为空");
        }
        if (StringUtils.isEmpty(entity.getName())) {
            throw new ValidationException("部门不能为空");
        }
        //2.保存数据
        int rows = sysDeptDao.insertObject(entity);
        //if(rows==1)
        //throw new ServiceException("save error");
        //3.返回数据
        return rows;
    }


}
