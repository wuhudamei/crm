package com.rocoinfo.entity.basicConfig;

import java.util.Date;

import com.rocoinfo.entity.IdEntity;

/**
 * 派发规则 实体类
 * 
 * @author Paul
 *
 */
public class DistributeRule extends IdEntity {
	
	//规则编号
	private String code;
	//规则名称
	private String name;
	//规则描述
	private String description;
	//状态--0:禁用;1:启用
	private String status;
	//创建时间
	private Date createTime;
	
	public DistributeRule(){
		
	}
	public DistributeRule(Long id){
		this.id = id;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
