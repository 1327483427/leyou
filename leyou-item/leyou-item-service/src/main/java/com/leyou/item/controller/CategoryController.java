package com.leyou.item.controller;

import com.leyou.item.pojo.Category;
import com.leyou.item.service.CategoryService;
import com.netflix.ribbon.proxy.annotation.Http;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 根据父节点id查询子节点
     * @param pid
     * @return
     */
    @GetMapping("list")
    public ResponseEntity<List<Category>> queryCategoriesByPid(@RequestParam(value = "pid",defaultValue = "0")Long pid){
            if (pid == null || pid < 0) {
                //400 参数不合法
//                return  ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                return ResponseEntity.badRequest().build();

            }
            List<Category> categories = this.categoryService.queryCategoriesByPid(pid);
            if (CollectionUtils.isEmpty(categories)) {
                //404 资源服务器未找到
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                return ResponseEntity.notFound().build();
            }
            //200 查询成功
            return ResponseEntity.ok(categories);
    }

    /**
     * 新增节点
     * @param category
     * @return
     */
    @PostMapping
    public ResponseEntity<Void> addCategory(Category category){
        this.categoryService.addCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 更新
     * @return
     */
    @PutMapping
    public ResponseEntity<Void> updateCategory(Category category){
        this.categoryService.updateCategory(category);
        return ResponseEntity.accepted().build();
    }

    /**
     * 删除节点
     * @param id
     * @return
     */
    @DeleteMapping("cid/{cid}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("cid")Long id){
        this.categoryService.deleteCategoryById(id);
        return ResponseEntity.ok().build();
    }

}
