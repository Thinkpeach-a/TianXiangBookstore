package com.wang.redsunstore.service.serviceImpl;

import com.wang.redsunstore.dao.UsersMapper;
import com.wang.redsunstore.entity.Users;
import com.wang.redsunstore.service.UserService;
import com.wang.redsunstore.utils.MD5Utils;
import com.wang.redsunstore.vo.ResStatus;
import com.wang.redsunstore.vo.ResultVo;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.beans.Transient;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UsersMapper usersMapper;

    @Override
    @Transactional
    public ResultVo userResgit(String name, String pwd) {
        synchronized (this){
            /*根据用户查询，该用户是否已经被注册*/
            Example example = new Example(Users.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("username",name);
            List<Users> users = usersMapper.selectByExample(example);

            /*如果用户不存在则注册，存在则返回用户已经注册
            * 如果返回的数据库改变行数大于则注册成功，反之失败*/
            if(users.size() == 0){
                String md5Pwd = MD5Utils.md5(pwd);
                Users user = new Users();
                user.setUsername(name);
                user.setPassword(md5Pwd);
                user.setUserImg("img/default.png");
                user.setUserRegtime(new Date());
                user.setUserModtime(new Date());
                int i = usersMapper.insert(user);
                if(i >0){
                    return new ResultVo(ResStatus.OK,"用户注册成功",user);
                }else{
                    return new ResultVo(ResStatus.NO,"用户注册失败",null);
                }
            }else{
                return new ResultVo(ResStatus.NO,"该用户已经被注册",null);
            }

        }
    }

    @Override
    public ResultVo checkLogin(String name, String pwd) {
        Example example = new Example(Users.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username", name);
        List<Users> users = usersMapper.selectByExample(example);
        if(users.size() == 0){
            return new ResultVo(ResStatus.NO,"登录失败，用户名不存在！",null);
        }else{
            String md5Pwd = MD5Utils.md5(pwd);
            if(md5Pwd.equals(users.get(0).getPassword())){
                //如果登录验证成功，则需要生成令牌token（token就是按照特定规则生成的字符串）
                //使用jwt规则生成token字符串

                JwtBuilder builder = Jwts.builder();

                HashMap<String,Object> map = new HashMap<>();
                map.put("key1","value1");
                map.put("key2","value2");

                String token = builder.setSubject(name)                     //主题，就是token中携带的数据
                        .setIssuedAt(new Date())                            //设置token的生成时间
                        .setId(users.get(0).getUserId() + "")               //设置用户id为token  id
                        .setClaims(map)                                     //map中可以存放用户的角色权限信息
                        .setExpiration(new Date(System.currentTimeMillis() + 24*60*60*10000)) //设置token过期时间
                        .signWith(SignatureAlgorithm.HS256,"wzq123")     //设置加密方式和加密密码
                        .compact();//生成token

                return new ResultVo(ResStatus.OK,token,users.get(0));
            }else{
                return new ResultVo(ResStatus.NO,"登录失败，密码错误！",null);
            }
        }
    }


}
