package com.wang.redsunstore.service.serviceImpl;

import com.wang.redsunstore.dao.UserAddrMapper;
import com.wang.redsunstore.entity.UserAddr;
import com.wang.redsunstore.service.UserAddrService;
import com.wang.redsunstore.vo.ResStatus;
import com.wang.redsunstore.vo.ResultVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserAddrServiceImpl implements UserAddrService {

    @Resource
    private UserAddrMapper userAddrMapper;


    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public ResultVo listUserAddrByUid(int userId) {
        Example example = new Example(UserAddr.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",userId);
        criteria.andEqualTo("status",1);
        List<UserAddr> userAddrs = userAddrMapper.selectByExample(example);
        return new ResultVo(ResStatus.OK,"success",userAddrs);

    }
}
