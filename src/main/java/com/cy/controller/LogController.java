package com.cy.controller;

import com.cy.entity.Log;
import com.cy.service.LogService;
import com.cy.vo.JsonResult;
import com.cy.vo.PageObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author wh
 * @date 2020/5/26
 * Description: 日志controller
 */
@Controller
@RequestMapping("/log")
public class LogController {
    @Autowired
    private LogService logService;

    /**
     * 日志分页查询
     *
     * @param username    用户名
     * @param pageCurrent 当前页
     * @return json
     */
    @RequestMapping("/doFindPageObjects")
    @ResponseBody
    public JsonResult queryLog(String username, Integer pageCurrent) {
        PageObject<Log> pageObjects = logService.findPageObjects(username, pageCurrent);
        return new JsonResult(pageObjects);
    }

    /**
     * 删除
     *
     * @param ids 日志ids
     * @return json结果
     */
    @RequestMapping("/doDeleteObjects")
    @ResponseBody
    public JsonResult doDeleteObjects(String ids) {
        logService.delete(ids);
        return new JsonResult("delete OK");
    }

}
