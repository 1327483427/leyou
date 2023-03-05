package com.leyou.item.mapper;

import com.leyou.item.pojo.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoryMapper extends tk.mybatis.mapper.common.Mapper<Category>{

    @Select("SELECT * FROM tb_category WHERE id IN (SELECT category_id FROM tb_category_brand WHERE brand_id = #{bid})")
    List<Category> queryCategoryByBrandId(Long bid);
}
