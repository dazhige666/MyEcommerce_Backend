package com.neuedu.boot002.web;

import com.neuedu.boot002.pojo.User;
import com.neuedu.boot002.service.IuserService;
import com.neuedu.boot002.util.ServeResponse;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/back")
//映射http请求到控制器方法
@RestController
//返回结果序列化为xml或json返回客户端
@CrossOrigin("*")
public class UserWeb {
    @Resource
    //依赖注入
    private IuserService service;

    @RequestMapping("/user")
    //请求user方法
    public ServeResponse user(Integer pageNumber,Integer pageSize,String username,Integer roleName,Integer status){
        //System.out.println(username+"  "+roleName+"   "+status);
        return service.getAll(pageNumber,pageSize,username,roleName,status);

    }
    @RequestMapping("/updateUserStatus")
    //更新用户状态
    public  ServeResponse updateStatus(Integer id,Boolean status) {
    return service.updateUserStatus(id,status);
    //System.out.println(id+"   "+status);}
}

    @RequestMapping("/likeName")
    public ServeResponse likeName(String username){
        //System.out.println(username);
        return service.likeName(username);
    }

    @RequestMapping("/checkUsername")
    public ServeResponse checkUsername (Integer id,String username){
        //System.out.println(id + " " +username);
        return service.checkUsername(id, username);
    }
    @RequestMapping("/edit")
    public ServeResponse edit(@RequestBody User user){
        //System.out.println(user);
        return service.edit(user);
    }

    @RequestMapping("/login")
    public ServeResponse login(@RequestBody User user ){
        System.out.println(user);
        return service.login(user);
    }



}
