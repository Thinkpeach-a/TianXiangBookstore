package com.wang.redsunstore.service.serviceImpl;

import com.wang.redsunstore.dao.ProductCommentsMapper;

import com.wang.redsunstore.entity.ProductComments;
import com.wang.redsunstore.entity.ProductCommentsVO;
import com.wang.redsunstore.service.ProductCommentsService;
import com.wang.redsunstore.utils.PageHelper;
import com.wang.redsunstore.vo.ResStatus;
import com.wang.redsunstore.vo.ResultVo;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service
public class ProductCommentsServiceImpl implements ProductCommentsService {

    @Resource
    private ProductCommentsMapper productCommentsMapper;

    @Override
    public ResultVo listCommentsByProductId(String productId, int pageNum, int limit) {
        //1.根据商品id查询总记录数
        Example example = new Example(ProductComments.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("productId",productId);
        int count = productCommentsMapper.selectCountByExample(example);

        //2.计算总页数（必须确定每页显示多少条  pageSize = limit）
        int pageCount = count%limit==0 ?count/limit:count/limit+1;

        //通过pageNum计算当前页的起始位置
        int start = (pageNum-1)*limit;

        ////3.查询当前页的数据（因为评论中需要用户信息，因此需要连表查询---自定义）
        List<ProductCommentsVO> productCommentsVOS = productCommentsMapper.selectProductComments(productId, start, limit);

        return new ResultVo(ResStatus.OK,"success",new PageHelper<ProductCommentsVO>(count,pageCount,productCommentsVOS));
    }

    @Override
    public ResultVo getCommentsCountByProductId(String productId) {
        //1.获取总评数
        Example example = new Example(ProductComments.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("productId",productId);
        int total = productCommentsMapper.selectCountByExample(example);

        //2.获取好评数
        Example example1 = new Example(ProductComments.class);
        Example.Criteria criteria1 = example1.createCriteria();
        criteria1.andEqualTo("productId",productId);
        criteria1.andEqualTo("commType",1);
        int goodTotal = productCommentsMapper.selectCountByExample(example1);

        //2.好评率
        double percent =Double.parseDouble(goodTotal+"")/Double.parseDouble(total+"")*100;
        String percentValue =(percent+"").substring(0,(percent+"").lastIndexOf(".")+3);

        //3.获取中评数
        Example example2 = new Example(ProductComments.class);
        Example.Criteria criteria2 = example2.createCriteria();
        criteria2.andEqualTo("productId",productId);
        criteria2.andEqualTo("commType",0);
        int midTotal = productCommentsMapper.selectCountByExample(example2);

        //4.获取差评数
        Example example3 = new Example(ProductComments.class);
        Example.Criteria criteria3 = example3.createCriteria();
        criteria3.andEqualTo("productId",productId);
        criteria3.andEqualTo("commType",-1);
        int badTotal = productCommentsMapper.selectCountByExample(example3);

        HashMap<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("goodTotal",goodTotal);
        map.put("midTotal",midTotal);
        map.put("badTotal",badTotal);
        map.put("percentValue",percentValue);

        return new ResultVo(ResStatus.OK,"success",map);
    }


}
