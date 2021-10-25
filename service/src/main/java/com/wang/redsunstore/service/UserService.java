package com.wang.redsunstore.service;

import com.wang.redsunstore.vo.ResultVo;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    //⽤户注册
    public ResultVo userResgit(String name, String pwd);
    //⽤户登录
    public ResultVo checkLogin(String name, String pwd);

}
