package com.rocoinfo.utils;

import java.util.Date;
import java.util.UUID;

/**
 * <dl>
 * <dd>Description: 美得你crm 生成编码</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017-6-12 19:01:41</dd>
 * <dd>@author：Andy</dd>
 * </dl>
 */
public class GenerateCodeUtil {

	private static final String CODE_TYPE_CANCEL = "02"; // 退单申请
	private static final String CODE_TYPE_BACK_ORDER = "01"; // 无效申请

	private static final String DATE_TYPE = "yyMMddHHmmss";
	
	
	private static final Integer INVITATION_CODE_SIZE = 8;

	/**
	 * 生成任务编号
	 * 
	 * @description 随机32位UUID
	 * @return
	 */
	public static String generateTaskCode() {
		String uuid = UUID.randomUUID().toString();
		uuid = uuid.replace("-", "");
		return uuid;
	}

	/**
	 * 生成 客户编号
	 * 
	 * @param source
	 *            数据来源
	 * @return
	 */
	public static String generateCustomerCode(String source) {
		String uuid = generateTaskCode();
		return source + DateUtils.format(new Date(), "yyMMdd") + uuid.substring(0, 5);
	}

	/**
	 * 生成 邀约码
	 * 
	 * @param store
	 *            门店如北京:bj,上海:sh
	 * @return
	 */
	public static String generateInvitationCode(String store) {
		Double d = (double)Math.random() * 99999 * Integer.parseInt(DateUtils.format(new Date(), "ddss"));
		return String.valueOf(d).replace(".", "").substring(0, INVITATION_CODE_SIZE);
	}
	
	/**
	 * 退单申请编号
	 * 
	 * @return
	 */
	public static String generateInvalidateApplyCode() {
		String code = CODE_TYPE_BACK_ORDER + DateUtils.format(new Date(), DATE_TYPE);
		return code;
	}

	/**
	 * 无效申请编号
	 * 
	 * @return
	 */
	public static String generateBackOrder() {
		String code = CODE_TYPE_CANCEL + DateUtils.format(new Date(), DATE_TYPE);
		return code;
	}

}
