package com.wang.redsunstore.dao;

import com.wang.redsunstore.entity.IndexImg;
import com.wang.redsunstore.general.GeneralDAO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IndexImgMapper extends GeneralDAO<IndexImg> {
    //查询轮播图信息，并且按照status=1，seq的顺序进行排序
    public List<IndexImg> listIndexImgs();
}