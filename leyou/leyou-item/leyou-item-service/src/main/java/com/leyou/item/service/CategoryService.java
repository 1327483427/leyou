package com.leyou.item.service;

import com.leyou.item.mapper.CategoryMapper;
import com.leyou.item.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    public Category queryCategoryById(Long id){
        return this.categoryMapper.selectByPrimaryKey(id);
    }

    public List<Category> queryCategoriesByPid(Long pid){
        Category record = new Category();
        record.setParentId(pid);
        return this.categoryMapper.select(record);
    }

    public int addCategory(Category category){
        //id自增，设置为空
        category.setId(null);
        //2.保存
        int count = this.categoryMapper.insert(category);
        //3.修改父节点
        Category parent = new Category();
        parent.setId(category.getParentId());
        parent.setIsParent(true);
        this.categoryMapper.updateByPrimaryKeySelective(parent);
        return count;
    }

    public int deleteCategoryById(Long id){
        //删除父节点
        List<Category> categoryList = this.queryCategoriesByPid(id);
        categoryList.forEach(category -> {
           this.categoryMapper.deleteByPrimaryKey(category.getId());
        });

        //删除当前节点
        return this.categoryMapper.deleteByPrimaryKey(id);
    }

    public int modifyCategory(Category category){
        Category newCategory = this.categoryMapper.selectByPrimaryKey(category.getId());
        newCategory.setName(category.getName());
        return this.categoryMapper.updateByPrimaryKeySelective(category);
    }
}
