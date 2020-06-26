package com.cy.mapper;

import com.cy.entity.Log;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author wh
 * @date 2020/5/26
 * Description: 日志dao层
 */
@Mapper
public interface LogMapper {

    /**
     * 分页查询日志信息
     *
     * @param username   用户名
     * @param startIndex 当前页的起始位置
     * @param pageSize   每页的条数
     * @return 日志集合
     */
    List<Log> findAllLogPage(String username, Integer startIndex, Integer pageSize);

    /**
     * 查询日志的总数
     *
     * @param username 用户名
     * @return 日志总数
     */
    int findAllLogCount(String username);

    /**
     * 删除
     *
     * @param list 日志ids
     */
    int deleteByIds(List list);
}
