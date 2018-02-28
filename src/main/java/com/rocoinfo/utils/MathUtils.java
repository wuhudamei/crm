package com.rocoinfo.utils;

/**
 * <dl>
 * <dd>Description: </dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017/4/13 下午2:33</dd>
 * <dd>@author：Aaron</dd>
 * </dl>
 */
public class MathUtils {

    public static boolean eq(Double n1, Double n2) {
        if (n1 == null || n2 == null) {
            return false;
        }
        return n1.equals(n2);
    }

    public static Double add(Double... numbers) {
        double sum = 0d;
        if (numbers != null && numbers.length > 0) {
            for (Double number : numbers) {
                if (number != null) {
                    sum += number;
                }
            }
        }
        return new Double(sum);
    }
}
