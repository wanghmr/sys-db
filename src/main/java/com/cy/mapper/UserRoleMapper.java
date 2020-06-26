package com.cy.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wh
 * @date 2020/5/26
 * Description: 角色dao层
 */
@Mapper
public interface UserRoleMapper {
    /**
     * 基于角色id删除角色和用户关系数据
     *
     * @param id
     * @return
     */
    @Delete("delete from sys_user_roles where role_id=#{id}")
    int deleteObjectsByRoleId(Integer id);

    /**
     * 负责将用户与角色的关系数据写入到数据库
     * @param userId 用户id
     * @param roleIds 多个角色id
     * @return
     */
    int insertObjects(@Param("userId")Integer userId,
                      @Param("roleIds")Integer[] roleIds);


    List<Integer> findRoleIdsByUserId(Integer id);

    int deleteObjectsByUserId(Integer userId);

}
