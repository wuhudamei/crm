package com.rocoinfo.enumeration;

/**
 * 任务节点状态  类型
 * (邀约,接待,签单,创建房屋)
 * @author Paul
 * 2017年6月16日
 */
public enum TaskNodeStatus {
	
 	INVITATION("邀约"), RECEPTION("接待"), SIGNING("签单"),AFTERSIGN("后续服务");

	TaskNodeStatus(String label) {
        this.label = label;
    }
    private String label;
    public String getLabel() {
        return label;
    }
}
