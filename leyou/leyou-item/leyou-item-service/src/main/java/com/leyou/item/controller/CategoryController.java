package com.leyou.item.controller;

import com.leyou.item.pojo.Category;
import com.leyou.item.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("list")
    public ResponseEntity<List<Category>> queryCategoriesByPid(@RequestParam(value = "pid", defaultValue = "0") Long pid) {     //使用requestParam必须要给一个默认值

        try {
            if (pid == null || pid < 0) {
//                return ResponseEntity.badRequest().build();
//                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                return ResponseEntity.badRequest().build();
            };
            List<Category> categoryList = this.categoryService.queryCategoriesByPid(pid);
            if (CollectionUtils.isEmpty(categoryList)) {
//                return ResponseEntity.notFound().build();
                return ResponseEntity.notFound().build();
            };
            return ResponseEntity.ok(categoryList);
        } catch (Exception e) {
            e.printStackTrace();
        };
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @PostMapping("addCategory")
    public ResponseEntity<Void> addCategory(@RequestBody Category category){
        if(this.categoryService.addCategory(category) == 1){
            return  ResponseEntity.status(HttpStatus.CREATED).build();
        };
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

}
