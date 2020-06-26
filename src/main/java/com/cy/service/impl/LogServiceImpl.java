package com.cy.service.impl;

import com.cy.common.ValidationException;
import com.cy.entity.Log;
import com.cy.mapper.LogMapper;
import com.cy.service.LogService;
import com.cy.vo.PageObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wh
 * @date 2020/5/26
 * Description:日志serviceImpl
 */
@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogMapper logMapper;

    /**
     * 通过此方法实现分页查询操作
     *
     * @param username    基于条件查询时的参数名
     * @param pageCurrent 当前的页码值
     * @return 当前页记录+分页信息
     */
    @Override
    public PageObject<Log> findPageObjects(String username, Integer pageCurrent) {
        //1.验证参数合法  不合法抛出IllegalArgumentException异常
        if (pageCurrent == null || pageCurrent < 1) {
            throw new IllegalArgumentException("当前页码不正确");
        }
        Integer pageSize = 3;
        Integer startIndex = (pageCurrent - 1) * pageSize;

        //2查询日志总数
        Integer logCount = logMapper.findAllLogCount(username);
        if (logCount == 0) {
            throw new ValidationException("系统没有查到对应记录");
        }

        //3查询所有的日志集合
        List<Log> allLogPage = logMapper.findAllLogPage(username, startIndex, pageSize);

        //4封装数据PageObject
        PageObject<Log> page = new PageObject<>();
        page.setPageCount((logCount - 1) / pageSize + 1);
        page.setPageCurrent(pageCurrent);
        page.setPageSize(pageSize);
        page.setRecords(allLogPage);
        page.setRowCount(logCount);
        return page;
    }

    /**
     * 删除
     *
     * @param ids 日志ids
     */
    @Override
    public void delete(String ids) {
        //校验ids
        if (StringUtils.isEmpty(ids)) {
            throw new ValidationException("未选中数据,删除失败！");
        }
        String[] id = ids.split(",");
        List idList=new ArrayList();
        for (String s : id) {
            idList.add(Integer.parseInt(s));
        }
        int deleteCount = logMapper.deleteByIds(idList);
        if (deleteCount==0){
            throw new ValidationException("系统删除0条记录");
        }
    }
}
