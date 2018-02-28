package com.rocoinfo.utils;

import com.rocoinfo.common.PropertyHolder;
import com.rocoinfo.dto.StatusDto;
import com.rocoinfo.utils.des.Md5Utils;

import org.apache.commons.lang3.StringUtils;

/**
 * <dl>
 * <dd>Description: 美得你crm CrmApi 签名工具类 </dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017/6/20</dd>
 * <dd>@author：Ryze</dd>
 * </dl>
 */

public class CrmApiSignatureUtil {

    /**
     * 做签名  对传入的参数 md5( 大写（ json+secretKey） )
     * @param json
     * @return
     */
    public  static Object makeSignature(String json ){
        StringBuilder stringBuilder = new StringBuilder();
        String secretKey = PropertyHolder.getSecretKey();
        StringBuilder append = stringBuilder.append(secretKey).append(json);
        String str = append.toString().toUpperCase();
        return StatusDto.buildSuccess("success", Md5Utils.generate(str)) ;
    }

    /**
     * 签名对比
     * @param json  参数
     * @param signature 签名
     * @return
     */
    public static Object signatureComparison(String json,String signature){
        if (StringUtils.isEmpty(signature)){
            StatusDto.buildFailure("传入参数有误");
        }
        String s = makeSignatureForComparison(json);
        if(s.equals(signature)){
            return  StatusDto.buildSuccess();
        }else {
            return  StatusDto.buildFailure("签名对比错误");
        }
    }
    /**
     * 做签名  对传入的参数 md5( 大写（ json+secretKey） ) 参数对比用
     * @param json
     * @return
     */
    public static String makeSignatureForComparison(String json ){
        StringBuilder stringBuilder = new StringBuilder();
        String secretKey = PropertyHolder.getSecretKey();
        StringBuilder append = stringBuilder.append(json).append(secretKey);
          return Md5Utils.generate(append.toString().toUpperCase());
    }
    
}
