package com.cy.service;

import com.cy.entity.Log;
import com.cy.vo.PageObject;

/**
 * @Author wh
 * @Date 2020/5/26 19:18
 * Description: 日志service
 */
public interface LogService {
    /**
     * 通过此方法实现分页查询操作
     *
     * @param username    基于条件查询时的参数名
     * @param pageCurrent 当前的页码值
     * @return 当前页记录+分页信息
     */
    PageObject<Log> findPageObjects(String username, Integer pageCurrent);

    /**
     * 删除
     *
     * @param ids 日志ids
     */
    void delete(String ids);
}
