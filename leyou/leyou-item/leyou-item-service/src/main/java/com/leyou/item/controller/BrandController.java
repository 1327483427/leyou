package com.leyou.item.controller;

import ch.qos.logback.core.joran.conditional.ElseAction;
import com.leyou.common.pojo.PageResult;
import com.leyou.item.pojo.Brand;
import com.leyou.item.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.LongFunction;

@Controller
@RequestMapping("brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping("page")
    public ResponseEntity<PageResult<Brand>> queryBrandsByPage(
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "desc", required = false) Boolean desc
    ) {
        PageResult<Brand> brandPageResult = this.brandService.queryBrandsByPage(key, page, rows, sortBy, desc);
        if (brandPageResult == null || CollectionUtils.isEmpty(brandPageResult.getItems())) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(brandPageResult);
    }

    @PostMapping
    public  ResponseEntity<Void> saveBrand(Brand brand, @RequestParam("cids") List<Long> cids){
        if(brand == null){
            return ResponseEntity.badRequest().build();
        }
        if(this.brandService.saveBrand(brand,cids) == 1) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @PutMapping
    public  ResponseEntity<Void> updateBrand(Brand brand,  @RequestParam("cids") List<Long> cids){
        if(brand == null){
            return ResponseEntity.badRequest().build();
        }

        if(this.brandService.updateBrand(brand,cids) == 1) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}
