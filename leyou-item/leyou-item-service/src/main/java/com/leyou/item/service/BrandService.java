package com.leyou.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.pojo.PageResult;
import com.leyou.item.mapper.BrandMapper;
import com.leyou.item.pojo.Brand;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class BrandService {
    @Autowired
    private BrandMapper brandMapper;

    /**
     * 根据查询条件分页并且排序查询分页信息
     * @param key
     * @param page
     * @param rows
     * @param sortBy
     * @param desc
     * @return
     */
    public PageResult<Brand> queryBrandsByPage(String key, Integer page, Integer rows, String sortBy, Boolean desc) {
        //初始化example对象
        Example example = new Example(Brand.class);
        Example.Criteria criteria = example.createCriteria();

        //key:根据name模糊查询。或者根据首字母查询
        if(StringUtils.isNotBlank(key)){
            criteria.andLike("name","%" + key + "%" ).orEqualTo("letter",key);
        }

        //添加分页条件
        PageHelper.startPage(page,rows);
        //添加排序条件
        if (StringUtils.isNotBlank(sortBy)){
            example.setOrderByClause(sortBy+ " " + (desc ? "desc" : "asc"));
        }
        List<Brand> brands = this.brandMapper.selectByExample(example);
        //包装成pageInfo
        PageInfo<Brand> pageInfo = new PageInfo<>(brands);
        //包装成分页结果集
        return new PageResult<>(pageInfo.getTotal(),pageInfo.getList());
    }

    /**
     * 新增品牌
     * @param brand
     * @param cids
     */
    @Transactional
    public void saveBrand(Brand brand, List<Long> cids) {
        //先新增Brand 会生成brand id
 //      Boolean flag =  this.brandMapper.insertSelective(brand) == 1;
        this.brandMapper.insertSelective(brand);
//       if(flag){  //添加事务，不用判断（统一成功和失败）
           cids.forEach(cid ->{
               this.brandMapper.insertCategoryAndBrand(cid,brand.getId());
           });
//       }
        //再新增中间表
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateBrand(Brand brand,List<Long> cids) {
        this.deleteByBrandIdInCategoryBrand(brand.getId());

        this.brandMapper.updateByPrimaryKeySelective(brand);
        //维护品牌和分类中间表
        for (Long cid : cids) {
            //System.out.println("cid:"+cid+",bid:"+brand.getId());
            this.brandMapper.insertCategoryAndBrand(cid, brand.getId());
        }
    }

    /**
     * 品牌删除
     * @param id
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteBrand(Long id) {
        //删除品牌信息
        brandMapper.deleteByPrimaryKey(id);
        //维护中间表
        brandMapper.deleteByBrandIdInCategoryBrand(id);
    }

    /**
     * 删除中间表中的数据
     * @param bid
     */
    public void deleteByBrandIdInCategoryBrand(Long bid) {
        brandMapper.deleteByBrandIdInCategoryBrand(bid);
    }

    /**
     * 根据分类id查询品牌列表
     * @param cid
     * @return
     */
    public List<Brand> queryBrandsByCid(Long cid) {
       return this.brandMapper.selectBrandsByCid(cid);

    }
}
