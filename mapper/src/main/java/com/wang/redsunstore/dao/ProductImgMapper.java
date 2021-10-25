package com.wang.redsunstore.dao;

import com.wang.redsunstore.entity.ProductImg;
import com.wang.redsunstore.general.GeneralDAO;

import java.util.List;

public interface ProductImgMapper extends GeneralDAO<ProductImg> {
    public List<ProductImg> selectProductImgs(int productId);
}