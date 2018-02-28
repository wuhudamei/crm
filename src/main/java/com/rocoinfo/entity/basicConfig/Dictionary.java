package com.rocoinfo.entity.basicConfig;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.rocoinfo.dto.DictTreeDto;
import com.rocoinfo.entity.IdEntity;

/**
 * @author Paul 2017年6月8日
 */
public class Dictionary extends IdEntity {

	private static final long serialVersionUID = 1L;
	
	// 编号
	private String code;
	// 名称
	private String name;
	// 父类Id
	private Long parentId;
	//父类名称
	private String parentName;
	//是否删除  0:未删除,1:已删除
	private String deleted;
	
	//排序值:越小越靠前
	private Integer sort;
	
	//备注
	private String remark;
	
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
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
	public String getDeleted() {
		return deleted;
	}
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	
	/**
     * 构建目录树,递归操作
     *
     * @return 目录树
     */
    public static DictTreeDto buildDictTree(DictTreeDto curCode, List<Dictionary> dictList) {
        if (CollectionUtils.isEmpty(dictList) || curCode == null) {
            return curCode;
        }
        //子节点数据集合
        List<DictTreeDto> childNodeList = new ArrayList<>();

        // 构造根节点
        Dictionary dict = null;
        for (int i = 0; i < dictList.size(); i++) {
        	dict = dictList.get(i);
            if (dict == null) {
                continue;
            }
            //当前的id等于遍历出的对象的父id
            if (curCode.getId().equals(dict.getParentId())) {
                DictTreeDto childNode = new DictTreeDto();
                childNode.setId(dict.getId());
                childNode.setDeleted(dict.getDeleted());
                //判断当前状态为1(已删除)时,给text添加红色显示
                if("1".equals(childNode.getDeleted())){
                	childNode.setText("<font color=\'red\'>" + dict.getName() + "(已删除)</font>");
                }else{
                	childNode.setText(dict.getName());
                }
                childNode.setCode(dict.getCode());
                //排序值
                childNode.setSort(dict.getSort());
                //页面展示状态
                childNode.setState(new DictState(false, false));
                childNodeList.add(childNode);
            }
        }
        if (CollectionUtils.isNotEmpty(childNodeList)) {
            // 设置子节点
            curCode.setChildren(childNodeList);
        }
        // 递归构造子节点
        for (DictTreeDto dto : childNodeList) {
        	buildDictTree(dto, dictList);
        }
        return curCode;
    }
}