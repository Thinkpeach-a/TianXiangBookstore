package com.wang.redsunstore.controller;

import com.wang.redsunstore.service.CategoryService;
import com.wang.redsunstore.service.IndexImgService;
import com.wang.redsunstore.service.ProductService;
import com.wang.redsunstore.vo.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/index")
@CrossOrigin
@Api(value = "提供首页数据显示所需的接口",tags = "首页管理")
public class IndexController {
    @Resource
    private IndexImgService indexImgService;

    @Resource
    private CategoryService categoryService;

    @Resource
    private ProductService productService;

    @ApiOperation("首页轮播图接口")
    @GetMapping("/indexImg")
    public ResultVo listIndexImg(){
        ResultVo resultVo = indexImgService.selectIndexImg();
        return resultVo;
    }

    @GetMapping("/category-list")
    @ApiOperation("商品分类查询接口")
    public ResultVo listCatetory(){
        ResultVo resultVo = categoryService.listCategory();
        return resultVo;
    }

    @GetMapping("/list-recommends")
    @ApiOperation("新品推荐接口")
    public ResultVo listRecommendProducts() {
        return productService.selectRecommendProduct();
    }

    @GetMapping("/category-recommends")
    @ApiOperation("首页分类推荐接口")
    public ResultVo listRecommendProductsByCategory(){
        return categoryService.listFirstLevelCategories();
    }

}
