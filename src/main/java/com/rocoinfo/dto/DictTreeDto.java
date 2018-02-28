package com.rocoinfo.dto;


import java.util.List;

import com.rocoinfo.entity.basicConfig.DictState;

/**
 * 字典数据的 树形结构dto
 */
public class DictTreeDto {

    public DictTreeDto() {
        super();
    }
    public DictTreeDto(Long id) {
		super();
		this.id = id;
	}
	public DictTreeDto(Long id, String text) {
        super();
        this.id = id;
        this.text = text;
    }

    //节点id--存数据id
    private Long id;
    //子节点
    private List<DictTreeDto> children;
    //文本信息
    private String text;
    //删除/未删除状态
    private String deleted;
    //展开状态
    private DictState state;
    //数据编号
    private String code;
    //排序值
    private Integer sort;
    
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<DictTreeDto> getChildren() {
        return children;
    }
    public void setChildren(List<DictTreeDto> children) {
        this.children = children;
    }
    public void setText(String text) {
        this.text = text;
    }
    public String getText() {
        return this.text;
    }
    public DictState getState() {
        return state;
    }
    public void setState(DictState state) {
        this.state = state;
    }
	public String getDeleted() {
		return deleted;
	}
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
}
