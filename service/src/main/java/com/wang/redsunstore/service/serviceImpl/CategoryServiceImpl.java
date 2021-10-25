package com.wang.redsunstore.service.serviceImpl;

import com.wang.redsunstore.dao.CategoryMapper;
import com.wang.redsunstore.entity.CategoryVO;
import com.wang.redsunstore.service.CategoryService;
import com.wang.redsunstore.vo.ResStatus;
import com.wang.redsunstore.vo.ResultVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Resource
    private CategoryMapper categoryMapper;

    /*查询包含三个级别的分类*/
    @Override
    public ResultVo listCategory() {
        List<CategoryVO> categoryVOS = categoryMapper.selectAllCategories(0);
        ResultVo resultVO = new ResultVo(ResStatus.OK, "success", categoryVOS);
        return resultVO;
    }

    @Override
    public ResultVo listFirstLevelCategories() {
        List<CategoryVO> categoryVOS = categoryMapper.selectFirstLevelCategories();
        ResultVo resultVO = new ResultVo(ResStatus.OK, "success", categoryVOS);
        return resultVO;
    }

    /**
     * 查询所有一级分类，同时查询当前一级分类下销量最高的6个商品
     * @return
     */

}
