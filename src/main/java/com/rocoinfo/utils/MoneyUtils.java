package com.rocoinfo.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * <dl>
 * <dd>Description: 格式化金钱的utils</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017/4/9 15:44</dd>
 * <dd>@author：Kong</dd>
 * </dl>
 */
public class MoneyUtils {


    /**
     * 默认格式化规则
     */
    public static final String DEFAULT_PATTREN = "#,###.##";

    /**
     * 保留两位小数，千元之间逗号分割
     */
    public static final String COMMA_DOUBLE_DECIMAL = "#,###.##";

    /**
     * 保留两位小数￥开头
     */
    public static final String YUAN_DOUBLE_DECIMAL = "￥,###.##";
    /**
     * 保留两位小数$开头
     */
    public static final String DOLLAR_DOUBLE_DECIMAL = "$,###.##";

    /**
     * 将金额格式化成每千元分割，保留两位小数
     *
     * @param momey 金额
     * @return 返回格式化后的字符串
     */
    public static String format(Double momey) {
        return format(momey, DEFAULT_PATTREN);
    }

    /**
     * 格式化金钱为指定格式
     *
     * @param money   金钱
     * @param pattern 格式化格式
     * @return 返回格式化后的字符串
     */
    public static String format(Double money, String pattern) {

        NumberFormat nf = new DecimalFormat(pattern);

        return nf.format(money);
    }
}
