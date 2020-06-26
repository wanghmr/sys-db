package com.cy.service;

import com.cy.entity.Dept;
import com.cy.vo.Node;

import java.util.List;
import java.util.Map;

/**
 * @Author wh
 * @Date 2020/5/27 19:10
 * Description:
 */
public interface DeptService {
    List<Map<String, Object>> findObjects();

    List<Node> findZTreeNodes();

    int saveObject(Dept entity);

    int updateObject(Dept entity);

    int deleteObject(Integer id);
}
