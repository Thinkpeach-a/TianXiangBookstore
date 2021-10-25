package com.wang.redsunstore.service;

import com.wang.redsunstore.vo.ResultVo;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {
    public ResultVo selectRecommendProduct();

    public ResultVo selectBasicInfoByProductId(String productId);
}
