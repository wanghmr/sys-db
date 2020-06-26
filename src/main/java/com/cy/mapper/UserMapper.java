package com.cy.mapper;

import com.cy.entity.User;
import com.cy.vo.UserDeptVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wh
 * @date 2020/6/16
 * Description: 用户mapper层
 */
@Mapper
public interface UserMapper {

    /**
     * 根据部门id查询用户的数量（部门有用户则不能删除）
     *
     * @param id
     * @return
     */
    int getUserCountByDeptId(Integer id);

    /**
     * 分页查询
     *
     * @param username   用户名
     * @param startIndex 起始位置
     * @param pageSize   页面大小
     * @return List<User>
     */
    List<User> findObjectsPage(@Param("username") String username,
                               @Param("startIndex") Integer startIndex,
                               @Param("pageSize") Integer pageSize);

    /**
     * 根据用户名查询用户的数量
     *
     * @param username 用户名
     * @return 用户数量
     */
    int userCountByUsername(@Param("username") String username);

    List<UserDeptVo> findPageObjects(
            @Param("username") String username,
            @Param("startIndex") Integer startIndex,
            @Param("pageSize") Integer pageSize);

    int getRowCount(@Param("username") String username);

    /**
     * 启用/禁用
     *
     * @param id           id
     * @param valid        状态
     * @param modifiedUser 修改用户
     * @return 修改的数据量
     */
    int validById(@Param("id") Integer id,
                  @Param("valid") Integer valid,
                  @Param("modifiedUser") String modifiedUser);


    /**
     * 负责将用户信息写入到数据库
     *
     * @param entity
     * @return
     */
    int insertObject(User entity);

    UserDeptVo findObjectById(Integer id);

    /**
     * 新增
     *
     * @param entity
     * @return
     */
    int updateObject(User entity);

    /**
     * 基于用户id修改用户的密码
     *
     * @param password 新的密码
     * @param salt     密码加密时使用的盐值
     * @param id       用户id
     * @return
     */
    int updatePassword(
            @Param("password") String password,
            @Param("salt") String salt,
            @Param("id") Integer id);

    /**
     * 根据用户名查询用户信息
     *
     * @param username 用户名
     * @return 用户信息实体
     */
    User findUserByUserName(String username);

}
