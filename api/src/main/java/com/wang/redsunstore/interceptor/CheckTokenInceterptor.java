package com.wang.redsunstore.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wang.redsunstore.vo.ResStatus;
import com.wang.redsunstore.vo.ResultVo;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
/*拦截器必须有配置类*/
@Component
public class CheckTokenInceterptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /*放行预检请求*/
        String method = request.getMethod();
        System.out.println(method);
        /*此处有预检机制，第一次不会拿到token*/
        if("OPTIONS".equalsIgnoreCase(method)){
            return true;
        }

        String token = request.getHeader("token");
        System.out.println(token);
        if (token == null){
            ResultVo resultVo = new ResultVo(ResStatus.LOGIN_FAIL_NOT, "token不存在", null);
        }else{
            try {
                JwtParser parser = Jwts.parser();
                parser.setSigningKey("wzq123"); //解析token的SigningKey必须和生成token时设置密码一致
                //如果token正确（密码正确，有效期内）则正常执行，否则抛出异常
                Jws<Claims> claimsJws = parser.parseClaimsJws(token);
                return true;
            }catch (ExpiredJwtException e){
                ResultVo resultVo = new ResultVo(ResStatus.LOGIN_FAIL_OVERDUE, "登录已经过期", null);
                doResponse(response,resultVo);
            }catch (UnsupportedJwtException e){
                ResultVo resultVo = new ResultVo(ResStatus.LOGIN_FAIL_OVERDUE, "不支持jwt异常", null);
                doResponse(response,resultVo);
            }catch (Exception e){
                ResultVo resultVo = new ResultVo(ResStatus.LOGIN_FAIL_OVERDUE, "其他异常", null);
                doResponse(response,resultVo);
            }
//            JwtParser parser = Jwts.parser();
//            parser.setSigningKey("wzq123"); //解析token的SigningKey必须和生成token时设置密码一致
//            //如果token正确（密码正确，有效期内）则正常执行，否则抛出异常
//            Jws<Claims> claimsJws = parser.parseClaimsJws(token);
//            return true;

        }
        return false;
    }

    public void doResponse(HttpServletResponse response,ResultVo resultVo) throws Exception{
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        String s = new ObjectMapper().writeValueAsString(resultVo);
        out.print(s);
        out.flush();
        out.close();
    };

}
