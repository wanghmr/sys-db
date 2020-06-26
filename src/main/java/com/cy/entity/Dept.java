package com.cy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author wh
 * @date 2020/5/27
 * Description: 部门实体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dept implements Serializable {

    private static final long serialVersionUID = -6839357489053883314L;
    private Integer id;
    private String name;
    private Integer parentId;
    private Integer sort;
    private String note;
    private Date createdTime;
    private Date modifiedTime;
    private String createdUser;
    private String modifiedUser;
}
