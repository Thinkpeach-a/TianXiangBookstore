package com.wang.redsunstore.service;

import com.wang.redsunstore.entity.ShoppingCart;
import com.wang.redsunstore.vo.ResultVo;
import org.springframework.stereotype.Service;

@Service
public interface ShoppingCartService {
    public ResultVo addShoppingCart(ShoppingCart cart);

    public ResultVo listShoppingCartsByUserId(int userId);

    public ResultVo updateCartNum(int cartId,int cartNum);

    public ResultVo listShoppingCartsByCids(String cids);
}
