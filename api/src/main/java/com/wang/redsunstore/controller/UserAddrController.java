package com.wang.redsunstore.controller;

import com.wang.redsunstore.service.UserAddrService;
import com.wang.redsunstore.vo.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@CrossOrigin
@Api(value = "提供收货地址相关接口",tags = "收货地址管理")
@RequestMapping("/useraddr")
public class UserAddrController {
    @Resource
    private UserAddrService userAddrService;

    @GetMapping("/list")
    @ApiOperation("查询收货地址接口")
    //@ApiImplicitParam(dataType = "int",name = "userId", value = "用户ID",required = true)
    public ResultVo listAddr(Integer userId, @RequestHeader("token") String token){
        ResultVo resultVo = userAddrService.listUserAddrByUid(userId);
        return resultVo;
    }

}
