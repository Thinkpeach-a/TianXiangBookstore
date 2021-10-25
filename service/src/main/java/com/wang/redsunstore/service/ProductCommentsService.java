package com.wang.redsunstore.service;

import com.wang.redsunstore.vo.ResultVo;

public interface ProductCommentsService {
    /**
     * 根据商品id实现评论的分页查询
     * @param productId 商品ID
     * @param pageNum 查询页码
     * @param limit 每页显示条数
     * @return
     */
    public ResultVo listCommentsByProductId(String productId,int pageNum,int limit);

    public ResultVo getCommentsCountByProductId(String productId);
}
