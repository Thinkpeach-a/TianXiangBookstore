package com.wang.redsunstore.service.serviceImpl;

import com.wang.redsunstore.dao.IndexImgMapper;
import com.wang.redsunstore.entity.IndexImg;
import com.wang.redsunstore.service.IndexImgService;
import com.wang.redsunstore.vo.ResStatus;
import com.wang.redsunstore.vo.ResultVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class IndexImgServiceImpl implements IndexImgService {
    @Resource
    private IndexImgMapper indexImgMapper;

    @Override
    public ResultVo selectIndexImg() {
        List<IndexImg> indexImgs = indexImgMapper.listIndexImgs();
        if (indexImgs.size() == 0){
            return new ResultVo(ResStatus.NO,"图片资源不存在",null);
        }else{
            return new ResultVo(ResStatus.OK,"success",indexImgs);
        }
    }
}
