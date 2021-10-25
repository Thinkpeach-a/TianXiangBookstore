package com.wang.redsunstore.service.serviceImpl;

import com.wang.redsunstore.dao.OrderItemMapper;
import com.wang.redsunstore.dao.OrdersMapper;
import com.wang.redsunstore.dao.ProductSkuMapper;
import com.wang.redsunstore.dao.ShoppingCartMapper;
import com.wang.redsunstore.entity.*;
import com.wang.redsunstore.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private ShoppingCartMapper shoppingCartMapper;

    @Resource
    private OrdersMapper ordersMapper;

    @Resource
    private OrderItemMapper orderItemMapper;

    @Resource
    private ProductSkuMapper productSkuMapper;

    @Override
    @Transactional
    //保存订单业务
    /**
     * 1.校验库存
     * 2.保存订单，此处不是真的把所有订单信息都持久化
     * 3.保存商品快照
     * 4.扣减库存
     * 5.删除购物车
     */
    public Map<String, String> addOrder(String cids, Orders order) throws SQLException {
        Map<String, String> map = new HashMap<>();
        List<Integer> list= new ArrayList<>();
        String[] arr = cids.split(",");
        //查询提交的购物车商品
        for(int i = 0; i <arr.length ; i++){
            list.add(Integer.parseInt(arr[i]));
        }
        List<ShoppingCartVO> shoppingCartVOS = shoppingCartMapper.selectShopcartByCids(list);

        //2.校验库存，
        String untitled = "";
        boolean f =true;
        for (ShoppingCartVO sc:shoppingCartVOS){

            if(Integer.parseInt(sc.getCartNum()) > sc.getSkuStock()){
                f = false;
            }
            //获取所有商品名称，以,分割拼接成字符串
            untitled = untitled+sc.getProductName()+",";
        }

        //3.保存订单
        if(f){
            order.setUntitled(untitled);
            order.setCreateTime(new Date());
            order.setStatus("1");
            String orderId = UUID.randomUUID().toString().replace("-", "");
            order.setOrderId(orderId);
            int i = ordersMapper.insert(order);

            //4.生成订单快照
            for (ShoppingCartVO sc: shoppingCartVOS){
                int cnum= Integer.parseInt(sc.getCartNum());
                //快照id
                String itemId = System.currentTimeMillis() + "" + (new Random().nextInt(89999) + 10000);

                OrderItem orderItem = new OrderItem(itemId, orderId, sc.getProductId(),
                        sc.getProductName(), sc.getProductImg(), sc.getSkuId(), sc.getSkuName(), new
                        BigDecimal(sc.getSellPrice()), cnum, new BigDecimal(sc.getSellPrice() * cnum), new Date(), new
                        Date(), 0);

                int insert = orderItemMapper.insert(orderItem);
            }

            //5.扣减库存,根据套餐id修改库存量
            for(ShoppingCartVO sc:shoppingCartVOS){
                String skuId = sc.getSkuId();
                int newStock = sc.getSkuStock() - Integer.parseInt(sc.getCartNum());

                ProductSku productSku = new ProductSku();
                productSku.setSkuId(skuId);
                productSku.setStock(newStock);
                productSkuMapper.updateByPrimaryKeySelective(productSku);
            }

            //6.根据购物车id删除购物车
            for(int cid:list){
                shoppingCartMapper.deleteByPrimaryKey(cid);
            }

            map.put("orderId",orderId);
            System.out.println(map.get("orderId"));
            map.put("productNames",untitled);
            return map;
        }else{

            //表示库存不足
            return null;
        }
    }


}
