package com.wang.redsunstore.service;

import com.wang.redsunstore.entity.Orders;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface OS {
    public Map<String,String> addOrder(String cids, Orders order);
}
