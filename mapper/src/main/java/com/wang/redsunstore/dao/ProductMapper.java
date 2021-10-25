package com.wang.redsunstore.dao;

import com.wang.redsunstore.entity.Product;
import com.wang.redsunstore.entity.ProductImg;
import com.wang.redsunstore.entity.ProductVO;
import com.wang.redsunstore.general.GeneralDAO;

import java.util.List;

public interface ProductMapper extends GeneralDAO<Product> {
    /*根据产品推荐商品*/
    public List<ProductVO> selectRecommendProduct();

    /*根据首页分类选择商品*/
    public List<ProductVO> selectTop6ByCategoryId(int categoryId);
}