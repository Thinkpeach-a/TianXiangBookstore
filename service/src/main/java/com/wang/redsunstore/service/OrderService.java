package com.wang.redsunstore.service;

import com.wang.redsunstore.entity.Orders;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.annotation.Order;

import java.sql.SQLException;
import java.util.Map;

@Service
public interface OrderService {
    public Map<String,String> addOrder(String cids, Orders order) throws SQLException;
}
