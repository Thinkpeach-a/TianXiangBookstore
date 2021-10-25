package com.wang.redsunstore.controller;

import com.wang.redsunstore.service.ProductCommentsService;
import com.wang.redsunstore.service.ProductService;
import com.wang.redsunstore.vo.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@CrossOrigin
@RequestMapping("/product")
@Api(value = "提供商品信息相关的接口",tags = "商品管理")
public class ProductController {
    @Resource
    private ProductService productService;

    @Resource
    private ProductCommentsService productCommentsService;

    @ApiOperation("商品基本信息查询接口")
    @GetMapping("/detail-info/{pid}")
    public ResultVo getProductBasicInfo(@PathVariable("pid") String pid){
        return productService.selectBasicInfoByProductId(pid);
    }

    @ApiOperation("商品评论信息查询接口")
    @GetMapping("/detail-commonts/{pid}")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "int",name = "pageNum", value = "当前页码",required = true),
            @ApiImplicitParam(dataType = "int",name = "limit", value = "每页显示条数",required = true)
    })
    public ResultVo getProductCommonts(@PathVariable("pid") String pid,int pageNum,int limit){
        return productCommentsService.listCommentsByProductId(pid,pageNum,limit);
    }

    @ApiOperation("商品评价统计查询接口")
    @GetMapping("/detail-commontscount/{pid}")
    public ResultVo getProductCommontsCount(@PathVariable("pid") String pid){
        return productCommentsService.getCommentsCountByProductId(pid);
    }
}
