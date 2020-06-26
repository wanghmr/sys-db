package com.cy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.Date;

/**
 * @author wh
 * @date 2020/5/27
 * Description: 日志实体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Log implements Serializable {
    private static final long serialVersionUID = 3797452787411866776L;

    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户操作
     */
    private String operation;

    /**
     * 请求方法
     */
    private String method;

    /**
     * 请求参数
     */
    private String params;

    /**
     * 执行时长(毫秒)
     */
    private Long time;

    /**
     * IP地址
     */
    private String ip;

    /**
     * 创建时间
     */
    private Date createdTime;

}
