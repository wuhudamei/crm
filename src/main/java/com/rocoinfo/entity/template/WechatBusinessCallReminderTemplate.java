package com.rocoinfo.entity.template;

/**
 * <dl>
 * <dd>Description: 微信业务电话提醒模板消息实体</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017-7-9 20:52:09</dd>
 * <dd>@author：Andy</dd>
 * </dl>
 */
public class WechatBusinessCallReminderTemplate {
	/** 模板消息	头部内容 **/
	private String head;
	/** 模板消息	详情路径**/
	private String url;
	/** 模板消息	接收者(此处为综管系统员工号)**/
	private String jobNo;
	/** 模板消息	参数1**/
	private String keyword1;
	/** 模板消息	参数2**/
	private String keyword2;
	
	/**
	 * 获取	微信模板消息头部信息
	 * @return
	 */
	public String getHead() {
		return head;
	}
	/**
	 * 设置	微信模板消息头部信息
	 * @param head
	 */
	public void setHead(String head) {
		this.head = head;
	}
	/**
	 * 获取	微信模板消息详情url
	 * @return
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * 设置	微信模板消息详情url
	 * @param url
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * 获取	微信模板消息接收者(综管平台员工号)
	 * @return
	 */
	public String getJobNo() {
		return jobNo;
	}
	/**
	 * 设置	微信模板消息接收者(综管平台员工号)
	 * @param jobNo
	 */
	public void setJobNo(String jobNo) {
		this.jobNo = jobNo;
	}
	/**
	 * 获取	微信模板消息	参数1
	 * @return
	 */
	public String getKeyword1() {
		return keyword1;
	}
	/**
	 * 设置	微信模板消息	参数1
	 * @param keyword1
	 */
	public void setKeyword1(String keyword1) {
		this.keyword1 = keyword1;
	}
	/**
	 * 获取	微信模板消息	参数2
	 * @return
	 */
	public String getKeyword2() {
		return keyword2;
	}
	/**
	 * 设置	微信模板消息	参数2
	 * @param keyword2
	 */
	public void setKeyword2(String keyword2) {
		this.keyword2 = keyword2;
	}
	
}
