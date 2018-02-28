package com.rocoinfo.rest.dict;

import com.google.common.collect.Maps;
import com.rocoinfo.common.BaseController;
import com.rocoinfo.dto.StatusBootTableDto;
import com.rocoinfo.dto.StatusDto;
import com.rocoinfo.dto.page.PageTable;
import com.rocoinfo.dto.page.Pagination;
import com.rocoinfo.entity.basicConfig.Dictionary;
import com.rocoinfo.service.basicConfig.DictionaryService;
import com.rocoinfo.utils.MapUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * /*
 * /*@author asher
 * /*@time 2017-03-08 14:23:39
 **/
@RestController
@RequestMapping(value = "/api/dict/dic")
public class MdniDictionaryController extends BaseController {
   /* @Autowired
    DictionaryService mdniDictionaryService;

    *//**
     * 数据字典分页列表
     * @param keyword
     * @param searchType
     * @param offset
     * @param limit
     * @param orderColumn
     * @param orderSort
     * @return
     *//*
    @RequestMapping("/list")
    public Object list(@RequestParam(value = "keyword", required = false) String keyword,
                       @RequestParam(value = "searchType", required = false) Long searchType,
                       @RequestParam(value = "offset", defaultValue = "0") int offset,
                       @RequestParam(value = "limit", defaultValue = "20") int limit,
                       @RequestParam(value = "sortName", defaultValue = "id") String orderColumn,
                       @RequestParam(value = "sortOrder", defaultValue = "DESC") String orderSort) {
        Map<String, Object> params = Maps.newHashMap();
        MapUtils.putNotNull(params, "keyword", keyword);
        MapUtils.putNotNull(params, "searchType", searchType);

        PageTable page=mdniDictionaryService.searchScrollPage(params, new Pagination(offset, limit));
        return StatusDto.buildSuccess(page);
    }

    *//**
     * 数据字典增加或修改
     *
     * @param mdniDictionary
     * @return
     *//*
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Object add(@RequestBody Dictionary mdniDictionary) {
        mdniDictionaryService.addOrUpdate(mdniDictionary);
        return StatusDto.buildSuccess("操作成功");
    }

    *//**
     * 删除数据字典
     *
     * @param id
     * @return
     *//*
    @RequestMapping("/delete/{id}")
    public Object delete(@PathVariable(value = "id") Long id) {
        mdniDictionaryService.deleteById(id);
        return StatusDto.buildSuccess("删除成功");
    }

    *//**
     * 获取父类代码
     * @return
     *//*
    @RequestMapping("/getNode/{type}")
    public Object getNode(@PathVariable("type") String type){
        return StatusDto.buildSuccess(mdniDictionaryService.getNode(Long.parseLong(type)));
    }

    *//**
     * 根据id获取用户信息
     *//*
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public StatusDto getById(@PathVariable(value = "id") Long id) {

        Dictionary dic = mdniDictionaryService.getById(id);
        if (dic == null) {
            return StatusDto.buildFailure("获取组织架构失败！");
        }
        return StatusDto.buildSuccess(dic);

    }

    *//**
     * 通过类型获取字典内容
     * @param parentType
     * @param type
     * @return
     *//*
    @RequestMapping(value = "/getByType")
    public Object getByType(@RequestParam(value = "parentType",defaultValue = "0") Integer parentType, @RequestParam(required = false) Integer type) {
        return StatusDto.buildSuccess(mdniDictionaryService.getByType(parentType, type));
    }*/
}