package com.wang.redsunstore.service.serviceImpl;

import com.wang.redsunstore.dao.ProductImgMapper;
import com.wang.redsunstore.dao.ProductMapper;
import com.wang.redsunstore.dao.ProductParamsMapper;
import com.wang.redsunstore.dao.ProductSkuMapper;
import com.wang.redsunstore.entity.*;
import com.wang.redsunstore.service.ProductService;
import com.wang.redsunstore.vo.ResStatus;
import com.wang.redsunstore.vo.ResultVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Resource
    private ProductMapper productMapper;

    @Resource
    public ProductSkuMapper productSkuMapper;

    @Resource
    private ProductImgMapper productImgMapper;

    @Override
    public ResultVo selectRecommendProduct() {
        List<ProductVO> productVOS = productMapper.selectRecommendProduct();
        return new ResultVo(ResStatus.OK,"success",productVOS);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public ResultVo selectBasicInfoByProductId(String productId) {
        Example example = new Example(Product.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("productId", productId);
        criteria.andEqualTo("productStatus", 1);
        List<Product> products = productMapper.selectByExample(example);
        if (products.size() > 0) {
            //商品套餐
            Example example1 = new Example(ProductSku.class);
            Example.Criteria criteria1 = example1.createCriteria();
            criteria1.andEqualTo("productId", productId);
            criteria1.andEqualTo("status", 1);
            List<ProductSku> productSkus = productSkuMapper.selectByExample(example1);



            //商品图片
            Example example2 = new Example(ProductImg.class);
            Example.Criteria criteria2 = example2.createCriteria();
            criteria2.andEqualTo("itemId", productId);
            List<ProductImg> productImgs = productImgMapper.selectByExample(example2);


            HashMap<String, Object> basicInfo = new HashMap<>();
            basicInfo.put("products", products);
            basicInfo.put("productSkus", productSkus);
            basicInfo.put("productImgs", productImgs);
            return new ResultVo(ResStatus.OK, "success", basicInfo);
        }else{
            return new ResultVo(ResStatus.NO,"查询的商品不存在！",null);
        }
    }
}
