package com.wang.redsunstore.vo;

import com.sun.org.apache.xpath.internal.objects.XObject;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/*lombok使用步骤，
1.必须添加依赖
2.必须添加插件*/
/*@Data*/
/*@NoArgsConstructor
@AllArgsConstructor*/
@ApiModel(value = "resultVO对象",description ="返回给前端的数据")
public class ResultVo {
    /*响应给前端的状态码*/
    @ApiModelProperty(value = "响应状态码",dataType = "int")
    private int code;

    @ApiModelProperty(value = "响应提示信息",dataType = "String")
    /*响应给前端的提示信息*/
    private String msg;

    @ApiModelProperty(value = "响应数据",dataType = "Object")
    /*响应给前端的数据*/
    private Object data;

    public ResultVo(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
