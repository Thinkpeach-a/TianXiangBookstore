package com.wang.redsunstore.dao;

import com.wang.redsunstore.entity.ShoppingCart;
import com.wang.redsunstore.entity.ShoppingCartVO;
import com.wang.redsunstore.general.GeneralDAO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface ShoppingCartMapper extends GeneralDAO<ShoppingCart> {
    public List<ShoppingCartVO> selectShopcartByUserId(int userId);

    public int updateCartnumByCartid(@Param("cartId") int cartId,
                                     @Param("cartNum") int cartNum);
    //1,8
    public List<ShoppingCartVO> selectShopcartByCids(List<Integer> cids);


}