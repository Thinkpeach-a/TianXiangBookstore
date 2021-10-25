package com.wang.redsunstore.controller;

import com.wang.redsunstore.entity.Users;
import com.wang.redsunstore.service.UserService;
import com.wang.redsunstore.vo.ResStatus;
import com.wang.redsunstore.vo.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@ResponseBody
@RequestMapping("/user")
@CrossOrigin
@Api(tags = "用户注册与登录功能",value = "用户管理")
public class UserController {
//    此处都要使用接口注入
    @Resource
    private UserService userService;

    @ApiOperation("用户注册接口")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "string",name = "username", value = "用户注册账号",required = true),
            @ApiImplicitParam(dataType = "string",name = "password", value = "用户注册密码",required = true)
    })
    @PostMapping("/regist")
    public ResultVo regist(@RequestBody Users user){
        ResultVo resultVo = userService.userResgit(user.getUsername(), user.getPassword());
        return resultVo;
    }

    /*@ApiOperation("校验token是否过期接口")
    @GetMapping("/check")
    public ResultVo userTokencheck(@RequestHeader("token") String token){
        return new ResultVo(10001,"success",null);
    }*/

    @ApiOperation("用户登录接口")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "string",name = "username", value = "用户登录账号",required = true),
            @ApiImplicitParam(dataType = "string",name = "password", value = "用户登录密码",required = true)
    })

    @GetMapping("/login")
    public ResultVo login(@RequestParam("username") String name,
                          @RequestParam(value = "password") String pwd){
        ResultVo resultVo = userService.checkLogin(name, pwd);
//        logger.info(resultV.getMsg());
        return resultVo;
    }

    @ApiOperation("校验token是否过期接口")
    @GetMapping("/check")
    public ResultVo userTokencheck(@RequestHeader("token") String token){
        return new ResultVo(ResStatus.OK,"success",null);
    }
}
