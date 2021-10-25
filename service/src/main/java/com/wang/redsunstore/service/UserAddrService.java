package com.wang.redsunstore.service;

import com.wang.redsunstore.vo.ResultVo;
import org.springframework.stereotype.Service;

@Service
public interface UserAddrService {
    public ResultVo listUserAddrByUid(int userId);
}
