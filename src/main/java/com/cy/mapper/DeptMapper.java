package com.cy.mapper;

import com.cy.entity.Dept;
import com.cy.vo.Node;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author wh
 * @date 2020/5/27
 * Description: 菜单mapper
 */
@Mapper
public interface DeptMapper {
    /**
     * 查询所有部门以及部门的上级菜单信息
     * @return
     */
    @Select("select c.*,p.name parentName from sys_depts c left join sys_depts p on c.parentId=p.id")
    List<Map<String,Object>> findObjects();

    @Delete("delete from sys_depts where id=#{id}")
    int deleteObject(Integer id);

    @Select("select id,name,parentId from sys_depts")
    List<Node> findZTreeNodes();

    int updateObject(Dept entity);
    int insertObject(Dept entity);

    @Select("select count(*) from sys_depts where parentId=#{id}")
    int getChildCount(Integer id);

    /**
     * 根据部门id查询部门名称
     * @param deptId 部门id
     * @return 部门名称
     */
    Dept findDeptNameByDeptId(Integer deptId);
}
