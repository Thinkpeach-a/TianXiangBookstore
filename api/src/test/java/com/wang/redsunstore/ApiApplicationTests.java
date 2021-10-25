package com.wang.redsunstore;

import com.wang.redsunstore.dao.CategoryMapper;
import com.wang.redsunstore.dao.ProductCommentsMapper;
import com.wang.redsunstore.dao.ProductMapper;
import com.wang.redsunstore.dao.ShoppingCartMapper;
import com.wang.redsunstore.entity.*;
import com.wang.redsunstore.service.*;
import com.wang.redsunstore.vo.ResultVo;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

//模块化开发以后，单元测试最好放到启动类来做，因为必须依赖springboot
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApiApplication.class)
class ApiApplicationTests {
    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private CategoryService categoryService;

    @Resource
    private ProductMapper productMapper;

    @Resource
    private ProductCommentsService productCommentsService;

    @Resource
    private ShoppingCartService shoppingCartService;

    @Resource
    private IndexImgService indexImgService;
    @Test
    void contextLoads2() {
        ResultVo resultVo = indexImgService.selectIndexImg();
        System.out.println(resultVo.getMsg());

    }

    @Test
    void contextLoads() {
        List<CategoryVO> categoryVOS = categoryMapper.selectFirstLevelCategories();
//        System.out.println(resultVo.getData());
        for (CategoryVO c1 : categoryVOS) {
            System.out.println(c1);
            for (ProductVO c2 : c1.getProducts()) {
                System.out.println("   " + c2);
                for (ProductImg c3 : c2.getImgs()) {
                    System.out.println("      " + c3);
                }
            }
        }
      /*  ResultVo resultVo = categoryService.listCategory();
        System.out.println(resultVo.getCode());*/
    }

    @Test
    void contextLoads1() {
        ShoppingCart cart = new ShoppingCart();
         cart.setCartId(2);
         cart.setCartNum("100");
         cart.setCartTime("4400");
         cart.setUserId("1");
         cart.setProductId("2");
         cart.setSkuId("2");
        ResultVo resultVo = shoppingCartService.addShoppingCart(cart);
        System.out.println(resultVo.getMsg());

    }

}
