package com.wang.redsunstore.controller;

import com.wang.redsunstore.entity.ShoppingCart;
import com.wang.redsunstore.service.ShoppingCartService;
import com.wang.redsunstore.vo.ResStatus;
import com.wang.redsunstore.vo.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.xml.transform.Result;

@Controller
@ResponseBody
@RequestMapping("/shopcart")
/*crossorigin用于解决跨域问题，后端专用*/
@CrossOrigin
@Api(value = "购物车业务相关接口",tags = "购物车管理")
public class ShopCartController {
    @Resource
    private ShoppingCartService shoppingCartService;



    @PostMapping("/add")
    @ApiOperation("添加购物车接口")
    public ResultVo addShoppingCart(@RequestBody ShoppingCart cart, @RequestHeader("token") String token){
        ResultVo resultVo = shoppingCartService.addShoppingCart(cart);
        return resultVo;
    }

    @GetMapping("/list")
    @ApiImplicitParam(dataType = "int",name = "userId", value = "用户ID",required = true)
    public ResultVo list(Integer userId,@RequestHeader("token")String token){
        ResultVo resultVo = shoppingCartService.listShoppingCartsByUserId(userId);
        return resultVo;
    }

    @PutMapping("/update/{cid}/{cnum}")
    public ResultVo updateNum(@PathVariable("cid") Integer cartId,
                              @PathVariable("cnum") Integer cartNum,
                              @RequestHeader("token") String token){
        ResultVo resultVO = shoppingCartService.updateCartNum(cartId, cartNum);
        return resultVO;
    }

    @GetMapping("/listbycids")
    @ApiImplicitParam(dataType = "String",name = "cids", value = "选择的购物车记录的id",required = true)
    public ResultVo listByCids(String cids, @RequestHeader("token")String token) {
        ResultVo resultVO = shoppingCartService.listShoppingCartsByCids(cids);
        return resultVO;
    }
}
