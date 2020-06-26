package com.cy.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 封装角色id与角色名称（用户新增，编辑）
 * @author wh
 */
@Data
public class CheckBox implements Serializable{
	private static final long serialVersionUID = 5065823170856122977L;
	private Integer id;
	private String name;
	
}
