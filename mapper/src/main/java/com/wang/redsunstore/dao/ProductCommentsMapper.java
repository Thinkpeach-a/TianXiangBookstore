package com.wang.redsunstore.dao;

import com.wang.redsunstore.entity.ProductComments;
import com.wang.redsunstore.entity.ProductCommentsVO;
import com.wang.redsunstore.general.GeneralDAO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductCommentsMapper extends GeneralDAO<ProductComments> {
    public List<ProductCommentsVO> selectProductComments(
            @Param("productId") String productId,
            @Param("start") int start,
            @Param("limit") int limit);
}