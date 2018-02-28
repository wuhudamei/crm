package com.rocoinfo.interceptor.sign;

import com.rocoinfo.common.PropertyHolder;
import com.rocoinfo.utils.JsonUtils;
import com.rocoinfo.utils.des.Md5Utils;
import org.apache.commons.lang3.StringUtils;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author liupengfei
 * @Description 新版的crmapi接口调用签名工具类
 * @Date Created in 2017/12/26 18:40
 */
public class NewCrmApiSignatureHelper {

    /**
     * 签名比对
     * @param jsonData 请求的json 数据
     * @return
     */
    public static boolean signAuth(String jsonData)
    {
        Map<String,Object> map= JsonUtils.fromJson(jsonData, HashMap.class);
        String key = "";
        if(map.containsKey("key"))
        {
            key = map.get("key").toString();
            map.remove("key");
        }
        String mykey=getKey(map,"");
        if(!key.equals(mykey))
        {
            return false;
        }
        return true;
    }



    /**
     * 获取参数加密后的加密key 大写
     * @param map
     * @param separator 分隔符
     * @return
     */
    public static  String getKey(Map<String,Object> map,String separator){
        Map<String,Object> sortMap=sortMapByKey(map);
        StringBuffer signSb= new StringBuffer();
        for (Map.Entry<String, Object> entry : sortMap.entrySet()) {
            if(!isArray(entry.getValue()))
            {
                signSb.append(entry.getValue().toString().replace("null","")).append(separator);
            }
        }
        signSb.append(PropertyHolder.getNewApiSecretKey());
        return Md5Utils.generate(signSb.toString()).toUpperCase();
    }

    /**
     * 使用 Map按key进行排序
     * @param map
     * @return
     */
    public static Map<String, Object> sortMapByKey(Map<String, Object> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        Map<String, Object> sortMap = new TreeMap<String, Object>(
                new MapKeyComparator());
        sortMap.putAll(map);
        return sortMap;
    }

    /**
     * 判断json 值是否为数组
     * @param obj  json 值
     * @return
     */
    public static boolean isArray(Object obj){
        boolean result = false;
        String strJson = obj.toString();
        if(StringUtils.isBlank(strJson))
        {
            return  result;
        }
        final char[] strChar = strJson.toCharArray();
        final char firstChar = strChar[0];
        final char lastChar = strChar[strChar.length-1];

        if(firstChar == '[' && lastChar == ']'){
            result = true;
        }
        return  result;
    }

}
class MapKeyComparator implements Comparator<String> {

    @Override
    public int compare(String str1, String str2) {

        return str1.compareTo(str2);
    }
}
