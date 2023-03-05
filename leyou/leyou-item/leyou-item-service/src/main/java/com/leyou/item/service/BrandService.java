package com.leyou.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.pojo.PageResult;
import com.leyou.item.mapper.BrandMapper;
import com.leyou.item.pojo.Brand;
import com.leyou.item.pojo.Category;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.util.List;

@Service
public class BrandService {

    @Autowired
    private BrandMapper brandMapper;


    public PageResult<Brand> queryBrandsByPage(String key, Integer page, Integer rows, String sortBy, Boolean desc) {

        Example example = new Example(Brand.class);
        Example.Criteria criteria = example.createCriteria();

        if(StringUtils.isNotBlank(key)){
            criteria.andLike("name","%"+key+"%").orEqualTo("letter",key);
        }

        PageHelper.startPage(page,rows);
        if (StringUtils.isNotBlank(sortBy)){
            example.setOrderByClause(sortBy+" " + (desc?"desc":"asc"));
        }
        List<Brand> brandList = this.brandMapper.selectByExample(example);
        PageInfo<Brand> pageInfo = new PageInfo<>(brandList);
        return new PageResult<>(pageInfo.getTotal(),pageInfo.getList());
    }

    @Transactional
    public int saveBrand(Brand brand, List<Long> cids){

        int i = this.brandMapper.insertSelective(brand);
        cids.forEach(cid ->{
            this.brandMapper.insertCategoryAndBrand(cid,brand.getId());
        });
        return i;
    }

    @Transactional
    public int updateBrand(Brand brand, List<Long> cids) {

        //1、更新brand
        int i = this.brandMapper.updateByPrimaryKey(brand);
        //2、删除历史brand_category
        this.brandMapper.deleteCategoryAndBrand(brand.getId());
        //3、新增目前的brand_category
        cids.forEach(cid ->{
            this.brandMapper.insertCategoryAndBrand(cid,brand.getId());
        });
        return i;
    }
}
