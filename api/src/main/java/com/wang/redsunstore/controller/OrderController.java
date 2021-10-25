package com.wang.redsunstore.controller;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConfig;
import com.wang.redsunstore.config.MypayConfig;
import com.wang.redsunstore.entity.Orders;
import com.wang.redsunstore.service.OrderService;
import com.wang.redsunstore.vo.ResStatus;
import com.wang.redsunstore.vo.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import sun.text.resources.cldr.ti.FormatData_ti_ER;
import tk.mybatis.mapper.annotation.Order;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/order")
@CrossOrigin
@Api(value = "提供订单数据显示所需的接口",tags = "订单管理")
public class OrderController {
    @Resource
    private OrderService orderService;

    @ApiOperation("添加订单快照并支付接口")
    @PostMapping("/add")
    public ResultVo add(String cids, @RequestBody Orders order) {
        ResultVo resultVo = null;
        //设置当前订单信息
        try{
            Map<String, String> orderInfo = orderService.addOrder(cids, order);
            String orderId = orderInfo.get("orderId");
            if (orderId !=null) {
                HashMap<String, String> data = new HashMap<>();
                data.put("body", orderInfo.get("productNames")); //商品描述
                data.put("out_trade_no", "12313221"); //使⽤当前⽤户订单的编号作为当前⽀付交易的交易号
                data.put("fee_type", "CNY"); //⽀付币种
                data.put("total_fee", order.getActualAmount() * 100 + ""); //⽀付⾦额
                data.put("trade_type", "NATIVE"); //交易类型
                data.put("notify_url", "/pay/success"); //设置⽀付完成时的回调⽅法接⼝

                //发送请求,获取响应
                //请求微信的支付链接
                WXPay wxPay = new WXPay(new MypayConfig());
                Map<String, String> resp = wxPay.unifiedOrder(data);
                System.out.println(resp);
                orderInfo.put("payUrl", resp.get("code_url"));

                resultVo = new ResultVo(ResStatus.OK, "提交订单成功！", orderInfo);
            } else {
                resultVo = new ResultVo(ResStatus.NO, "提交订单失败！", null);
            }
        }catch (SQLException e){
            resultVo = new ResultVo(ResStatus.NO,"提交订单失败！sql异常",null);

        }catch (Exception e){
            e.printStackTrace();
        }

      return resultVo;

    }
}
