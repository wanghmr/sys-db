package com.cy.mapper;

import com.cy.entity.Role;
import com.cy.vo.CheckBox;
import com.cy.vo.SysRoleMenuVo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author wh
 * @date 2020/5/26
 * Description: 角色dao层
 */
@Mapper
public interface RoleMapper {
    /**
     * 分页查询角色信息
     *
     * @param startIndex 上一页的结束位置
     * @param pageSize   每页要查询的记录数
     * @return
     */
    List<Role> findPageObjects(@Param("name") String name,
                               @Param("startIndex") Integer startIndex,
                               @Param("pageSize") Integer pageSize);

    /**
     * 查询记录总数
     *
     * @return
     */
    int getRowCount(@Param("name") String name);

    @Delete("delete from sys_roles where id=#{id}")
    int deleteObject(Integer id);

    int insertObject(Role entity);

    SysRoleMenuVo findObjectById(Integer id);

    int updateObject(Role entity);

    @Select("select id,name from sys_roles")
    List<CheckBox> findObjects();

}
