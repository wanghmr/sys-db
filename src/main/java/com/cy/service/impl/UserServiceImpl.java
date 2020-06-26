package com.cy.service.impl;

import com.cy.common.ValidationException;
import com.cy.entity.Dept;
import com.cy.entity.User;
import com.cy.mapper.DeptMapper;
import com.cy.mapper.UserMapper;
import com.cy.mapper.UserRoleMapper;
import com.cy.service.UserService;
import com.cy.vo.PageObject;
import com.cy.vo.UserDeptVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @author wh
 * @date 2020/6/16
 * Description:用户service层
 */
@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final DeptMapper deptMapper;
    private final UserRoleMapper userRoleMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, DeptMapper deptMapper, UserRoleMapper userRoleMapper) {
        this.userMapper = userMapper;
        this.deptMapper = deptMapper;
        this.userRoleMapper = userRoleMapper;
    }

    /**
     * 分页查询
     * 方案一：比较复杂
     *
     * @param username    用户名
     * @param pageCurrent 当前页码值
     * @return 封装数据
     */
    @Override
    public PageObject<UserDeptVo> findObjectsPage(String username, Integer pageCurrent) {
        Integer pageSize = 3;
        if (pageCurrent == null || pageCurrent < 1) {
            throw new IllegalArgumentException("当前页码不正确");
        }
        Integer startIndex = (pageCurrent - 1) * pageSize;
        List<User> userList = userMapper.findObjectsPage(username, startIndex, pageSize);
        int rowCount = userMapper.userCountByUsername(username);
        if (rowCount == 0) {
            throw new ValidationException("记录不存在");
        }
        ArrayList<UserDeptVo> userDeptVosList = new ArrayList<>();
        for (User user : userList) {
            Integer deptId = user.getDeptId();
            Dept dept = deptMapper.findDeptNameByDeptId(deptId);
            UserDeptVo userDeptVo = new UserDeptVo();
            userDeptVo.setId(user.getId());
            userDeptVo.setUsername(user.getUsername());
            userDeptVo.setPassword(user.getPassword());
            userDeptVo.setSalt(user.getSalt());
            userDeptVo.setEmail(user.getEmail());
            userDeptVo.setMobile(user.getMobile());
            userDeptVo.setValid(user.getValid());
            userDeptVo.setSysDept(dept);
            userDeptVo.setCreatedTime(user.getCreatedTime());
            userDeptVo.setModifiedTime(user.getModifiedTime());
            userDeptVo.setCreatedUser(user.getCreatedUser());
            userDeptVo.setModifiedUser(user.getModifiedUser());
            userDeptVosList.add(userDeptVo);
        }
        //封装数据
        PageObject<UserDeptVo> pageObject = new PageObject<UserDeptVo>();
        pageObject.setPageSize(pageSize);
        pageObject.setRowCount(rowCount);
        pageObject.setPageCurrent(pageCurrent);
        pageObject.setRecords(userDeptVosList);
        pageObject.setPageCount((rowCount - 1) / pageSize + 1);
        return pageObject;
    }

    /**
     * 分页查询
     * 方案二：优化的
     *
     * @param username    用户名
     * @param pageCurrent 当前页码值
     * @return 封装数据
     */
    @Override
    public PageObject<UserDeptVo> findPageObjects(String username,
                                                  Integer pageCurrent) {
        //1.数据合法性验证
        if (pageCurrent == null || pageCurrent <= 0) {
            throw new ValidationException("参数不合法");
        }
        //2.依据条件获取总记录数
        int rowCount = userMapper.getRowCount(username);
        if (rowCount == 0) {
            throw new ValidationException("记录不存在");
        }
        //3.计算startIndex的值
        int pageSize = 3;
        int startIndex = (pageCurrent - 1) * pageSize;
        //4.依据条件获取当前页数据
        List<UserDeptVo> records = userMapper.findPageObjects(username, startIndex, pageSize);
        //5.封装数据
        PageObject<UserDeptVo> pageObject = new PageObject<>();
        pageObject.setPageCount((rowCount - 1) / pageSize + 1);
        pageObject.setPageCurrent(pageCurrent);
        pageObject.setRowCount(rowCount);
        pageObject.setPageSize(pageSize);
        pageObject.setRecords(records);
        return pageObject;
    }

    /**
     * 启用/禁用
     *
     * @param id           用户id
     * @param valid        状态
     * @param modifiedUser 修改用户
     * @return 数据
     */
    @Override
    public int validById(Integer id, Integer valid, String modifiedUser) {
        //1.合法性验证
        if (id == null || id <= 0) {
            throw new ValidationException("参数不合法,id=" + id);
        }
        if (valid != 1 && valid != 0) {
            throw new ValidationException("参数不合法,valie=" + valid);
        }
        if (StringUtils.isEmpty(modifiedUser)) {
            throw new ValidationException("修改用户不能为空");
        }
        //2.执行禁用或启用操作
        int rows = 0;
        try {
            rows = userMapper.validById(id, valid, modifiedUser);
        } catch (Throwable e) {
            e.printStackTrace();
            //报警,给维护人员发短信
            throw new ValidationException("底层正在维护");
        }
        //3.判定结果,并返回
        if (rows == 0) {
            throw new ValidationException("此记录可能已经不存在");
        }
        return rows;
    }

    /**
     * 用户新增
     *
     * @param entity  用户实体
     * @param roleIds 角色ids
     * @return 新增记录数
     */
    @Override
    public int saveObject(User entity, Integer[] roleIds) {
        //1.验证数据合法性
        if (entity == null) {
            throw new ValidationException("保存对象不能为空");
        }
        if (StringUtils.isEmpty(entity.getUsername())) {
            throw new ValidationException("用户名不能为空");
        }
        if (StringUtils.isEmpty(entity.getPassword())) {
            throw new ValidationException("密码不能为空");
        }
        if (roleIds == null || roleIds.length == 0) {
            throw new ValidationException("至少要为用户分配角色");
        }

        //2.将数据写入数据库
        String salt = UUID.randomUUID().toString();
        entity.setSalt(salt);
        //加密(先了解,讲shiro时再说)
        SimpleHash sHash = new SimpleHash("MD5", entity.getPassword(), salt, 1);
        entity.setPassword(sHash.toHex());

        int rows = userMapper.insertObject(entity);
        //"1,2,3,4";
        userRoleMapper.insertObjects(entity.getId(), roleIds);
        //3.返回结果
        return rows;
    }

    /**
     * 编辑数据回显
     *
     * @param userId 用户id
     * @return 数据
     */
    @Override
    public Map<String, Object> findObjectById(Integer userId) {
        //1.合法性验证
        if (userId == null || userId <= 0) {
            throw new ValidationException("参数数据不合法,userId=" + userId);
        }
        //2.业务查询
        UserDeptVo user = userMapper.findObjectById(userId);
        if (user == null) {
            throw new ValidationException("此用户已经不存在");
        }
        List<Integer> roleIds = userRoleMapper.findRoleIdsByUserId(userId);
        //3.数据封装
        Map<String, Object> map = new HashMap<>();
        map.put("user", user);
        map.put("roleIds", roleIds);
        return map;
    }

    /**
     * 编辑
     *
     * @param entity  用户实体
     * @param roleIds 角色ids
     * @return 编辑的数量
     */
    @Override
    public int updateObject(User entity, Integer[] roleIds) {
        //1.参数有效性验证
        if (entity == null) {
            throw new IllegalArgumentException("保存对象不能为空");
        }
        if (StringUtils.isEmpty(entity.getUsername())) {
            throw new IllegalArgumentException("用户名不能为空");
        }
        if (roleIds == null || roleIds.length == 0) {
            throw new IllegalArgumentException("必须为其指定角色");
        }
        //其它验证自己实现，例如用户名已经存在，密码长度，...
        //2.更新用户自身信息
        int rows = userMapper.updateObject(entity);
        //3.保存用户与角色关系数据
        userRoleMapper.deleteObjectsByUserId(entity.getId());
        userRoleMapper.insertObjects(entity.getId(),
                roleIds);
        //4.返回结果
        return rows;
    }

    /**
     * 修改密码
     *
     * @param pwd    原密码
     * @param newPwd 新密码
     * @param cfgPwd 确认密码
     */
    @Override
    public void doUpdatePassword(String pwd, String newPwd, String cfgPwd) {
        if (StringUtils.isEmpty(pwd)) {
            throw new ValidationException("原密码不能为空");
        }
        if (StringUtils.isEmpty(newPwd)) {
            throw new ValidationException("新密码不能为空");
        }
        if (StringUtils.isEmpty(cfgPwd)) {
            throw new ValidationException("确认密码不能为空");
        }
        if (!newPwd.trim().equals(cfgPwd.trim())) {
            throw new ValidationException("新密码与确认密码不一样");
        }
        //获取登陆用户
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        SimpleHash sh = new SimpleHash("MD5", pwd, user.getSalt(), 1);
        if (!user.getPassword().equals(sh.toHex())) {
            throw new IllegalArgumentException("原密码不正确");
        }
        //3.对新密码进行加密
        String salt = UUID.randomUUID().toString();
        sh = new SimpleHash("MD5", newPwd, salt, 1);
        //4.将新密码加密以后的结果更新到数据库
        int rows = userMapper.updatePassword(sh.toHex(), salt, user.getId());
        if (rows == 0) {
            throw new ValidationException("更新密码失败");
        }
    }

}
