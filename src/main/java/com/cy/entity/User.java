package com.cy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author wh
 * @date 2020/5/27
 * Description: 用户实体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 63882295606154655L;
    private Integer id;
    private String username;
    //md5
    private String password;
    /**
     * 盐值(加密盐-辅助加密,保证密码更加安全)
     */
    private String salt;
    private String email;
    private String mobile;
    /**
     * 用户状态:1表示启用,0表示禁用
     */
    private Integer valid = 1;
    /**
     * 用户所在部门的部门信息
     */
    private Integer deptId;
    private Date createdTime;
    private Date modifiedTime;
    private String createdUser;
    private String modifiedUser;
}
