package com.leyou.item.service;

import com.leyou.item.mapper.CategoryMapper;
import com.leyou.item.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    /**
     *根据父节点查询子节点
     * @param pid
     * @return
     */
    public List<Category> queryCategoriesByPid(Long pid) {
        Category record = new Category();
        record.setParentId(pid);
        return this.categoryMapper.select(record);
    }

    /**
     * 新增category节点
     *
     * @param category
     */
    public void addCategory(Category category){
        /**
         * 首先设置id为空
         * 向数据库添加节点
         * 设置父节点isParent 为true
         */
        category.setId(null);
        this.categoryMapper.insert(category);
        Category parent = new Category();
        parent.setId(category.getParentId());
        parent.setIsParent(true);
        this.categoryMapper.updateByPrimaryKeySelective(parent);
    }

    /**
     * 根据id删除category
     * @param id
     */
    public void  deleteCategoryById(Long id){
        Category category = this.categoryMapper.selectByPrimaryKey(id);
        if (category.getIsParent()){
            //如果当前节点是父节点，删除该节点以及所有子节点
            List<Category> leafNodeList = new ArrayList<>();
            queryAllLeafNode(category,leafNodeList);
            //查找所有子节点
            List<Category> nodeList = new ArrayList<>();
            queryAllNode(category,nodeList);
            //删除所有子节点数据
            for (Category c:nodeList){
                this.categoryMapper.delete(c);
            }
//            //维护中间表
//            for (Category c:leafNodeList){
//                this.categoryMapper.deleteByCategoryIdInCategoryBrand(c.getId());
//            }
        }else{
            //当前节点不是父节点
            // 1、查询当前节点父节点有几个孩子（兄弟个数）
            Example example = new Example(Category.class);
            example.createCriteria().andEqualTo("parentId",category.getParentId());
            List<Category> list = this.categoryMapper.selectByExample(example);
            if (list.size()!=1){
                //有兄弟，直接删除自己
                this.categoryMapper.deleteByPrimaryKey(category.getId());
            }else{
                //没有兄弟，删除自己，父节点isParent改为false
                this.categoryMapper.deleteByPrimaryKey(category.getId());
                Category parent = new Category();
                parent.setId(category.getParentId());
                parent.setIsParent(false);
                this.categoryMapper.updateByPrimaryKeySelective(parent);
            }
        }
    }

    /**
     * 修改分类
     * @param category
     */
    public void updateCategory(Category category) {
        this.categoryMapper.updateByPrimaryKeySelective(category);
    }

    /**
     * 查找本节点下所有叶子节点
     * @param category
     * @param leafNode
     */
    private void queryAllLeafNode(Category category,List<Category> leafNode){
        if (!category.getIsParent()){
            leafNode.add(category);
        }
        Example example = new Example(Category.class);
        example.createCriteria().andEqualTo("parentId",category.getId());
        List<Category> list = this.categoryMapper.selectByExample(example);
        for (Category category1:list){
            queryAllLeafNode(category1,leafNode);
        }
    }

    /**
     * 查找本节点下所有节点
     * @param category
     * @param node
     */
    private void queryAllNode(Category category,List<Category> node){
        node.add(category);
        Example example = new Example(Category.class);
        example.createCriteria().andEqualTo("parentId",category.getId());
        List<Category> list = this.categoryMapper.selectByExample(example);
        for (Category category1:list){
            queryAllLeafNode(category1,node);
        }
    }



}
