package com.leyou.item.service;

import com.leyou.item.mapper.SpecGroupMapper;
import com.leyou.item.mapper.SpecParamMapper;
import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecificationService {

    @Autowired
    private SpecGroupMapper groupMapper;

    @Autowired
    private SpecParamMapper paramMapper;

    /**
     * 根据分类id查询分组
     * @param cid
     * @return
     */
    public List<SpecGroup> queryGroupsByCid(Long cid) {
        SpecGroup record = new SpecGroup();
        record.setCid(cid);
        return this.groupMapper.select(record);
    }

    /**
     * 根据条件查询规格参数
     * @param gid
     * @return
     */
    public List<SpecParam> queryParams(Long gid,Long cid,Boolean generic,Boolean searching) {
        SpecParam param = new SpecParam();
        param.setGroupId(gid);
        param.setCid(cid);
        param.setGeneric(generic);
        param.setSearching(searching);
        return this.paramMapper.select(param);
    }

    /**
     * 新增分组
     * @param specGroup
     */
    public void saveSpecGroup(SpecGroup specGroup) {
        this.groupMapper.insert(specGroup);
    }

    /**
     * 修改分组
     * @param specGroup
     */
    public void updateSpecGroup(SpecGroup specGroup) {
        this.groupMapper.updateByPrimaryKeySelective(specGroup);
    }

    /**
     * 删除分组
     * @param specGroup
     */
    public void deleteSpecGroup(SpecGroup specGroup) {
        this.groupMapper.deleteByPrimaryKey(specGroup);
    }

    /**
     * 新增参数
     * @param specParam
     */
    public void saveSpecParam(SpecParam specParam) {
        this.paramMapper.insert(specParam);
    }

    /**
     * 修改参数
     * @param specParam
     */
    public void updateSpecParam(SpecParam specParam) {
        this.paramMapper.updateByPrimaryKeySelective(specParam);
    }

    /**
     * 删除参数
     * @param specParam
     */
    public void deleteSpecParam(SpecParam specParam) {
        this.paramMapper.deleteByPrimaryKey(specParam);
    }
}
