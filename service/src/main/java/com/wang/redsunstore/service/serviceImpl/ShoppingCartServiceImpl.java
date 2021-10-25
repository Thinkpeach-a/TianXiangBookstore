package com.wang.redsunstore.service.serviceImpl;

import com.wang.redsunstore.dao.ShoppingCartMapper;
import com.wang.redsunstore.entity.ShoppingCart;
import com.wang.redsunstore.entity.ShoppingCartVO;
import com.wang.redsunstore.service.ShoppingCartService;
import com.wang.redsunstore.vo.ResStatus;
import com.wang.redsunstore.vo.ResultVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Resource
    private ShoppingCartMapper shoppingCartMapper;

    @Override
    public ResultVo addShoppingCart(ShoppingCart cart) {
        int i = shoppingCartMapper.insert(cart);
        //除了查询直接返回结果以外，增删改都需要返回操作是否成功
        if (i > 0){
            return new ResultVo(ResStatus.OK,"success",null);
        }else {
            return new ResultVo(ResStatus.NO,"fail",null);
        }

    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public ResultVo listShoppingCartsByUserId(int userId) {
        List<ShoppingCartVO> list = shoppingCartMapper.selectShopcartByUserId(userId);
        ResultVo resultVo = new ResultVo(ResStatus.OK, "success", list);
        return resultVo;
    }

    @Override
    public ResultVo updateCartNum(int cartId, int cartNum) {
        int i = shoppingCartMapper.updateCartnumByCartid(cartId, cartNum);
        if(i>0){
            return new ResultVo(ResStatus.OK,"update success",null);
        }else{
            return new ResultVo(ResStatus.NO,"update fail",null);
        }
    }

    @Override
    public ResultVo listShoppingCartsByCids(String cids) {
        //使用tkmapper只能查询到某张表中拥有的字段，因此没法查询到商品名称、图片、单价等信息
        String[] arr = cids.split(",");
        List<Integer> cartIds = new ArrayList<>();
        for (int i=0; i<arr.length; i++){
            cartIds.add(Integer.parseInt(arr[i]));
        }
        List<ShoppingCartVO> list = shoppingCartMapper.selectShopcartByCids(cartIds);
        ResultVo resultVO = new ResultVo(ResStatus.OK, "success", list);
        return resultVO;
    }


}
