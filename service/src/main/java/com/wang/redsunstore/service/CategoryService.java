package com.wang.redsunstore.service;

import com.wang.redsunstore.vo.ResultVo;

public interface CategoryService {
    public ResultVo listCategory();
    public ResultVo listFirstLevelCategories();

}
