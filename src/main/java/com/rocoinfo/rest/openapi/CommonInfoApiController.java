package com.rocoinfo.rest.openapi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rocoinfo.dto.StatusDto;
import com.rocoinfo.entity.basicConfig.Dictionary;
import com.rocoinfo.service.basicConfig.DictionaryService;

/**
 * 功能描述:门店、推广来源等集团通用基础数据的
 * @author phil
 * 2017年7月11日
 */

@RestController
@RequestMapping("/common/api/base")
public class CommonInfoApiController {

	public static String psParentCode = "TGLY";
	
	public static String mdParentCode = "MD";
	
	@Autowired
	private DictionaryService dicService;
	
	
	
	/**
	 * 
	 * 函数功能描述:查询所有推广来源
	 * @return
	 */
	@RequestMapping("/tgly")
	public Object getAllPromoteSourceInfo(){
		try{
			Map<String,String> sourceMap = new HashMap<String, String>();
			
			//查到所有推广来源
			List<Dictionary> allPromoteSrc = dicService.findSubDictListByCode(psParentCode);
			for (Dictionary promSrc : allPromoteSrc) {
				sourceMap.put(promSrc.getName(), promSrc.getCode());
			}
			return StatusDto.buildSuccess("查询成功", sourceMap);
		}
		catch (Exception e) {
			e.printStackTrace();
			return StatusDto.buildFailure("查询所有推广来源时程序 异常");
		}
	}
	
	
	
	/**
	 * 
	 * 函数功能描述:查询所有门店
	 * @return
	 */
	@RequestMapping("/md")
	public Object getAllStoreInfo(){
		try{
			Map<String,String> storeMap = new HashMap<String, String>();
			
			//查到所有推广来源
			List<Dictionary> allStore = dicService.findSubDictListByCode(mdParentCode);
			for (Dictionary store : allStore) {
				storeMap.put(store.getName(), store.getCode());
			}
			return StatusDto.buildSuccess("查询成功", storeMap);
		}
		catch (Exception e) {
			e.printStackTrace();
			return StatusDto.buildFailure("查询所有门店时程序 异常");
		}
	}
}
