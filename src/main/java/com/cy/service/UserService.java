package com.cy.service;

import com.cy.entity.User;
import com.cy.vo.PageObject;
import com.cy.vo.UserDeptVo;

import java.util.Map;

/**
 * @Author wh
 * @Date 2020/6/16
 * Description:
 */
public interface UserService {

    PageObject<UserDeptVo> findObjectsPage(String username, Integer pageCurrent);

    PageObject<UserDeptVo> findPageObjects(String username, Integer pageCurrent);

    int validById(Integer id, Integer valid, String modifiedUser);

    /**
     * 用户新增
     *
     * @param entity  用户实体
     * @param roleIds 角色ids
     * @return 新增记录数
     */
    int saveObject(User entity, Integer[] roleIds);

    /**
     * 编辑数据回显
     *
     * @param userId 用户id
     * @return 数据
     */
    Map<String, Object> findObjectById(Integer userId);
    /**
     * 编辑
     *
     * @param entity  用户实体
     * @param roleIds 角色ids
     * @return 编辑的数量
     */
    int updateObject(User entity,Integer[] roleIds);
    /**
     * 修改密码
     *
     * @param pwd    原密码
     * @param newPwd 新密码
     * @param cfgPwd 确认密码
     */
    void doUpdatePassword(String pwd, String newPwd, String cfgPwd);
}
