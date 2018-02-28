package com.rocoinfo.rest.basicConfig;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rocoinfo.common.BaseController;
import com.rocoinfo.dto.DictTreeDto;
import com.rocoinfo.dto.StatusDto;
import com.rocoinfo.entity.basicConfig.DictState;
import com.rocoinfo.entity.basicConfig.Dictionary;
import com.rocoinfo.service.basicConfig.DictionaryService;

/**
 * 数据字典
 * @author Paul
 * 2017年6月8日
 */
@RestController
@RequestMapping(value = "/api/dict")
public class DictionaryRestController extends BaseController {
    @Autowired 
    private DictionaryService dictionaryService;

    /**
	 * 获取树
	 * @return
	 */
	@RequestMapping("/tree")
	public Object tree() {
		List<Dictionary> list = dictionaryService.findAllWithDeleted();
		//将0作为父id,构建树
		DictTreeDto tree = Dictionary.buildDictTree(new DictTreeDto(0L), list);
		List<DictTreeDto> childrenList = tree.getChildren();
		if(childrenList != null && childrenList.size() > 0){
			for (DictTreeDto dict : childrenList) {
				dict.setState(new DictState(true, false));
			}
		}
		return StatusDto.buildSuccess(childrenList);
	}
    
    /**
	 * 添加或编辑 数据字典
	 *
	 * @param Dictionary 字典对象
	 * @return
	 */
	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public Object saveOrUpdate(Dictionary dictionary) {
		
		if (dictionaryService.validateCodeAvailable(dictionary)) {
			return StatusDto.buildFailure("编码重复！");
		}
		
		dictionaryService.saveOrUpdate(dictionary);
		
		//通过id和名称name创建字典树对象,并返回当前对象
		DictTreeDto dto = new DictTreeDto(dictionary.getId(), dictionary.getName());
		//设置排序值
		dto.setSort(dictionary.getSort());
		return StatusDto.buildSuccess(dto);
	}
    
    /**
     * 删除数据字典
     */
    @RequestMapping("/deleteById")
    public Object deleteById(Dictionary dictionary) {
    	if(dictionary == null || dictionary.getId() == null || StringUtils.isBlank(dictionary.getName())){
    		return StatusDto.buildFailure("数据字典id或名称为空!");
    	}
    	//判断该节点下是否还有子节点
        boolean result = dictionaryService.checkHasChildNode(dictionary.getId());
        if(result){
        	//有,提示不能删除
        	return StatusDto.buildFailure("该数据下还有子节点数据,请先删除子节点数据后,再尝试删除该数据!");
        }
        int flag = dictionaryService.deleteById(dictionary);
        if(flag > 0){
        	return StatusDto.buildSuccess("删除成功!");
        }
        return StatusDto.buildFailure("删除失败!");
    }

    /**
     * 根据id获取 字典对象
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Object getById(@PathVariable(value = "id") Long id) {

        Dictionary dic = dictionaryService.getById(id);
        if (dic == null) {
            return StatusDto.buildFailure("获取数据字典失败！");
        }
        
        return StatusDto.buildSuccess(dic);
    }
    
    /**
     * 根据code 获取所有子节点数据;该code下的所有各级子类数据
     * @return 返回树类型数据
     */
    @RequestMapping(value = "/findChildrenByCode", method = RequestMethod.GET)
    public Object findChildrenByCode( String code) {
    	if(StringUtils.isBlank(code)){
    		return StatusDto.buildFailure("数据字典编码code不能为空!");
    	}
    	//查所有启用的数据字典
    	List<Dictionary> list = dictionaryService.findAll();
		//通过code查找出该数据的id
    	Long id = null;
    	if(list != null && list.size() > 0){
    		for (Dictionary dict : list) {
    			if(code.equals(dict.getCode())){
    				id = dict.getId();
    				break;
    			}
			}
    	}
    	//没找到 返回提示信息:
    	if(id == null){
    		return StatusDto.buildFailure("数据字典编码code不存在");
    	}
		DictTreeDto tree = Dictionary.buildDictTree(new DictTreeDto(id), list);
		List<DictTreeDto> childrenList = tree.getChildren();
		if(childrenList != null && childrenList.size() > 0){
			for (DictTreeDto dict : childrenList) {
				dict.setState(new DictState(true, false));
			}
		}
		return StatusDto.buildSuccess(childrenList);
    }
    
    
    
    /**
     * 根据code 获取所有子节点数据
     * @return 返回子节点数据数组
     */
    @RequestMapping(value = "/findSubDictListByCode", method = RequestMethod.GET)
    public Object findSubDictListByCode( String code) {
    	if(StringUtils.isBlank(code)){
    		return StatusDto.buildFailure("数据字典编码code不能为空!");
    	}
    	//查所有启用的数据字典
    	return StatusDto.buildSuccess(dictionaryService.findSubDictListByCode(code));
    }
}