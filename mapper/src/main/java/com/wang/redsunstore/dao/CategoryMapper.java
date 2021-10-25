package com.wang.redsunstore.dao;

import com.wang.redsunstore.entity.Category;
import com.wang.redsunstore.entity.CategoryVO;
import com.wang.redsunstore.general.GeneralDAO;

import java.util.List;

public interface CategoryMapper extends GeneralDAO<Category> {
    /*根据子查询进行分类查询，查询全部分类*/
    public List<CategoryVO> selectAllCategories(int parentId);

    /*根据子查询查询产品的推荐分类,查询一级类别*/
    public List<CategoryVO> selectFirstLevelCategories();
}
