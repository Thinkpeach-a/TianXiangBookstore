package com.wang.redsunstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
/*启动类扫描mapper下的dao包*/
/*尽管现在是模块化开发，但是其他模块的包必须层级在启动类的包之下
* 即以启动类的包名为前缀开发，后面的可以自定义，如：com.wang.redsunstore.dao*/
/*此处mapperscan必须换成tkmapper的以tk开头的依赖*/
@MapperScan("com.wang.redsunstore.dao")
public class ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

}
