package com.rocoinfo.service.basicConfig;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rocoinfo.common.service.CrudService;
import com.rocoinfo.dto.StatusDto;
import com.rocoinfo.entity.basicConfig.Dictionary;
import com.rocoinfo.repository.basicConfig.DictionaryDao;

/**
/*
/*@author asher
/*@time 2017-03-08 14:23:39
**/
@Service
public class DictionaryService  extends CrudService<DictionaryDao,Dictionary> {

	@Autowired
	private DictionaryDao dictionaryDao;
	
	/**
	 * 检查字典编码code是否重复
	 */
	public boolean validateCodeAvailable(Dictionary dictionary) {
		String code = dictionary.getCode();
		if (dictionary == null || StringUtils.isBlank(dictionary.getCode())) {
			return false;
		}
		return dictionaryDao.getByCode(dictionary.getCode(), dictionary.getId()) != null;
	}
	
	/**
	 * 添加或编辑 字典数据
	 *
	 * @param Dictionary 字典对象
	 * @return
	 */
	@Transactional
	public Object saveOrUpdate(Dictionary dictionary) {
		if (dictionary.getId() == null) {
			//新增
			//默认:未删除
			dictionary.setDeleted("0");
			this.entityDao.insert(dictionary);
		} else {
			//修改
			this.entityDao.update(dictionary);
		}

		return StatusDto.buildSuccess();
	}

	/**
	 * 通过id校验 是否含有子节点(查询未删除状态的)
	 * @param id
	 * @return
	 */
	public boolean checkHasChildNode(Long id) {
		//通过id 查找子节点的个数
		int count = dictionaryDao.countChildNodesById(id);
		if(count > 0){
			//有
			return true;
		}
		return false;
	}

	/**
	 * 查询所有(包含已删除的)
	 * @return
	 */
	public List<Dictionary> findAllWithDeleted() {
		return dictionaryDao.findAllWithDeleted();
	}

	/**
	 * 删除数据字典对象
	 */
	public int deleteById(Dictionary dictionary) {
		return dictionaryDao.deleteById(dictionary);
	}

	/**
	 *  根据dicCode 查询子类列表
	 * @param dicCode
	 * @return
	 */
	public List<Dictionary> findSubDictListByCode( String dicCode){
		return  entityDao.findSubDictListByCode(dicCode);
	}
	
	/**
	 * 根据dicCode查询本对象
	 */
	public Dictionary getDictionaryByCode(String code){
		return dictionaryDao.getDictionaryByCode(code);
	}
}