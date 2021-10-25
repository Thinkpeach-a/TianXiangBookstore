package com.wang.redsunstore.controller;

import com.github.wxpay.sdk.WXPay;
import com.wang.redsunstore.config.MypayConfig;
import com.wang.redsunstore.entity.Orders;
import com.wang.redsunstore.service.OrderService;
import com.wang.redsunstore.vo.ResStatus;
import com.wang.redsunstore.vo.ResultVo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/orders")
@Api(value = "提供订单相关的操作接口",tags = "订单dsdd管理")
public class TestController {
    @Resource
    private OrderService orderService;

    @PostMapping("/add")
    public ResultVo add(String cids, @RequestBody Orders order) {
        ResultVo resultVO = null;
        try {
            Map<String, String> orderInfo = orderService.addOrder(cids, order);
            if (orderInfo != null) {
                String orderId = orderInfo.get("orderId");
                System.out.println(orderId);
                //设置当前订单信息
                HashMap<String, String> data = new HashMap<>();
                data.put("body", orderInfo.get("productNames"));  //商品描述
                data.put("out_trade_no", orderId);               //使用当前用户订单的编号作为当前支付交易的交易号
                data.put("fee_type", "CNY");                     //支付币种
                //data.put("total_fee",order.getActualAmount()*100+"");          //支付金额
                data.put("total_fee", "1");
                data.put("trade_type", "NATIVE");                //交易类型
                data.put("notify_url", "http://47.118.45.73:8080/pay/callback");           //设置支付完成时的回调方法接口

                //发送请求，获取响应
                //微信支付：申请支付连接
                WXPay wxPay = new WXPay(new MypayConfig());
                Map<String, String> resp = wxPay.unifiedOrder(data);
                System.out.println(resp);
                orderInfo.put("payUrl", resp.get("code_url"));
                //orderInfo中包含：订单编号，购买的商品名称，支付链接
                resultVO = new ResultVo(ResStatus.OK, "提交订单成功！", orderInfo);
            } else {
                resultVO = new ResultVo(ResStatus.NO, "提交订单失败！", null);
            }
        } catch (SQLException e) {
            resultVO = new ResultVo(ResStatus.NO, "提交订单失败！", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultVO;
    }
}
