package com.rocoinfo.repository.basicConfig;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.rocoinfo.common.persistence.CrudDao;
import com.rocoinfo.entity.basicConfig.Dictionary;


/**
 * @author Paul
 * 2017年6月8日
 */
@Repository
public interface DictionaryDao extends CrudDao<Dictionary> {

	/**
	 * 通过字典编码 获得字典数据
	 * 
	 * @param code 编码
	 * @param excludeProductId 需要排除的字典Id
	 */
    Dictionary getByCode(@Param("code") String code, @Param("excludeDictId") Long excludeDictId);

    /**
     * 通过id 查找子节点的个数
     * @param id
     * @return 个数
     */
	int countChildNodesById(Long id);

	/**
	 * 查询所有(包含已删除的)
	 * @return
	 */
	List<Dictionary> findAllWithDeleted();

	/**
	 * 删除数据字典对象,并给该数据字典值 加上颜色标记
	 * @param dictionary
	 * @return
	 */
	int deleteById(Dictionary dictionary);

	/**
	 *  根据dicCode 查询子类列表
	 * @param dicCode
	 * @return
	 */
	List<Dictionary> findSubDictListByCode(@Param("dicCode") String dicCode);

	/**
	 * 根据dicCode查询本对象
	 */
	Dictionary getDictionaryByCode(String code);

}