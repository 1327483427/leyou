package com.leyou.item.mapper;

import com.leyou.item.pojo.Brand;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Mapper
public interface BrandMapper extends tk.mybatis.mapper.common.Mapper<Brand> {

    @Insert("INSERT INTO tb_category_brand(category_id, brand_id) VALUES (#{cid},#{bid})")
    int insertCategoryAndBrand(Long cid, Long bid);

    @Delete("DELETE FROM tb_category_brand WHERE brand_id = #{bid}")
    void deleteCategoryAndBrand(Long bid);
}

