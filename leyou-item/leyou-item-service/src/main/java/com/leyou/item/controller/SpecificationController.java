package com.leyou.item.controller;

import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import com.leyou.item.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("spec")
public class SpecificationController {
    @Autowired
    private SpecificationService specificationService;

    /**
     * 根据分类id查询分组
     * @param cid
     * @return
     */
    @GetMapping("groups/{cid}")
    public ResponseEntity<List<SpecGroup>> queryGroupsByCid(@PathVariable("cid")Long cid){
        List<SpecGroup> groups = this.specificationService.queryGroupsByCid(cid);
        if(CollectionUtils.isEmpty(groups)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(groups);
    }

    /**
     * 根据条件查询规格参数
     * @param gid
     * @return
     */
    @GetMapping("params")
    public ResponseEntity<List<SpecParam>> queryParams(
            @RequestParam(value = "gid",required = false)Long gid,
            @RequestParam(value = "cid",required = false)Long cid,
            @RequestParam(value = "generic",required = false)Boolean generic,
            @RequestParam(value = "searching",required = false)Boolean searching
            ){
        List<SpecParam> params = this.specificationService.queryParams(gid,cid,generic,searching);
        if (CollectionUtils.isEmpty(params)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(params);
    }

    /**
     * 新增分组
     * @param specGroup
     * @return
     */
    @PostMapping("group")
    public ResponseEntity<Void> saveSpecGroup(@RequestBody SpecGroup specGroup){
        this.specificationService.saveSpecGroup(specGroup);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 修改分组
     * @param specGroup
     * @return
     */
    @PutMapping("group")
    public ResponseEntity<Void> updateSpecGroup(@RequestBody SpecGroup specGroup){
        this.specificationService.updateSpecGroup(specGroup);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 删除分组
     * @param id
     * @return
     */
    @DeleteMapping("group/{id}")
    public ResponseEntity<Void> deleteSpecGroup(@PathVariable("id")Long id){
        SpecGroup specGroup = new SpecGroup();
        specGroup.setId(id);
        this.specificationService.deleteSpecGroup(specGroup);
        return ResponseEntity.ok().build();
    }

    /**
     * 新增参数
     * @param specParam
     * @return
     */
    @PostMapping("param")
    public ResponseEntity<Void> saveSpecGroup(@RequestBody SpecParam specParam){
        this.specificationService.saveSpecParam(specParam);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 修改参数
     * @param specParam
     * @return
     */
    @PutMapping("param")
    public ResponseEntity<Void> updateSpecParam(@RequestBody SpecParam specParam){
        this.specificationService.updateSpecParam(specParam);
        return ResponseEntity.ok().build();
    }

    /**
     * 删除参数
     * @param id
     * @return
     */
    @DeleteMapping("param/{id}")
    public ResponseEntity<Void> deleteSpecParam(@PathVariable("id")Long id){
        SpecParam specParam = new SpecParam();
        specParam.setId(id);
        this.specificationService.deleteSpecParam(specParam);
        return ResponseEntity.ok().build();
    }



}
