package com.neuedu.boot002.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.neuedu.boot002.dao.UserDao;
import com.neuedu.boot002.pojo.User;
import com.neuedu.boot002.util.ServeResponse;
import com.neuedu.boot002.vo.userVo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements IuserService {
    @Resource
    private UserDao dao;

    @Override
    public ServeResponse getAll(Integer pageNumber, Integer pageSize, String username, Integer roleName, Integer status) {
        if(pageNumber==null||pageNumber<=1)
            pageNumber = 1;
        if(pageSize==null||pageSize<=0)
            pageSize = 12;

        QueryWrapper<User> qw = new QueryWrapper<>();
        if(!(username == null||"".equals(username))){
            qw.like("username",username);
        }
        if(roleName != null){
            qw.eq("role",roleName);
        }
        if(status!= null){
            qw.eq("status",status);
        }
        IPage page = new Page<>(pageNumber,pageSize);
        dao.selectPage(page,qw);

        long p1 = page.getTotal()%pageSize==0? page.getTotal()/pageSize:page.getTotal()/pageSize+1;
        if(pageNumber>p1)
            page.setCurrent(1);
        //如果传入的页码数大于查询出的页码数，数据会因为页码过大无法显示，所以令当前页为1

        page.setRecords(vos(page.getRecords()));
        return ServeResponse.success("成功获取数据",page);
    }

    @Override
    public ServeResponse updateStatus(Integer id, Boolean status) {
        User u =  new User();
        u.setId(id);
        u.setUpdateTime(LocalDateTime.now());
        u.setStatus(status? 1:0 );
        int i = dao.updateById(u);
        return (i==1? ServeResponse.success("修改成功"):ServeResponse.error("修改失败"));
    }

    @Override
    public ServeResponse likeName(String username) {

        return null;
    }

    @Override
    public ServeResponse checkUsername(Integer id, String username) {
        QueryWrapper<User> q1 = new QueryWrapper();
        q1.eq("id",id);
        User u1 = dao.selectOne(q1);
        if("".equals(username)&&id==0)
            return ServeResponse.error("用户名不可为空");
        //不能添加id为0，用户名为空的用户
        else if(u1==null){
            if(dao.selectOne(new QueryWrapper<User>().eq("username",username))==null)
                return ServeResponse.success("用户名可以使用添加");
            else return ServeResponse.error("用户名已存在");
        }
        //数据库不存在传入id的用户，此时判断是否存在相同用户名的用户
        //都不存在说明是在添加用户，且可以添加
        else if(u1.getUsername().equals(username))
            return ServeResponse.error("当前用户名和原来用户名一致");
        //数据库存在传入id用户，且其用户名与传入的用户名相同
        else {
            QueryWrapper<User> q2 = new QueryWrapper();
            q2.eq("username",username);
            q2.ne("id",id);
            User u2 = dao.selectOne(q2);
            //查询数据库是否有与传入值相同的用户名，没有则可以修改
            if(u2 == null){
                return ServeResponse.success("用户名不存在，可以修改");
            }else return ServeResponse.error("用户名被占用");

        }
    }
    @Override
    public ServeResponse edit(User user) {
        if(user.getId()>0){
            user.setUpdateTime(LocalDateTime.now());
            int update = dao.updateById(user);
            if(update>0)
                return ServeResponse.success("修改成功");
            else return ServeResponse.error("修改失败");
        }else{
            user.setCreateTime(LocalDateTime.now());
            user.setUpdateTime(LocalDateTime.now());
            user.setStatus(1);

            user.setRole(1);
            if(dao.insert(user)>0)
                return ServeResponse.success("添加成功");
            else return ServeResponse.error("添加失败");
        }
    }
    @Override
    public ServeResponse login(User user) {
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("username",user.getUsername());
        qw.eq("password",user.getPassword());
        qw.eq("role",user.getRole());
        User u = dao.selectOne(qw);
        if(u == null)
            return ServeResponse.error("输入错误") ;
        else {
            u.setPassword("");
            return ServeResponse.success("登录成功",u);
        }

    }

    //对封装数据进行处理
    public List<userVo> vos(List<User> users){
        List<userVo> vos = new ArrayList<>();
        for(User u:users){
            userVo vo = new userVo();
            vo.setPassword(u.getPassword());
            vo.setId(u.getId());
            vo.setUsername(u.getUsername());
            vo.setPhone(u.getPhone());
            vo.setEmail(u.getEmail());
            vo.setCreateTime(u.getCreateTime());
            vo.setUpdateTime(u.getUpdateTime());
            vo.setStatus(u.getStatus() == 1);
            switch (u.getRole()) {
                case 2 -> vo.setRole("管理员");
                case 1 -> vo.setRole("卖家");
                case 0 -> vo.setRole("买家");
            }
            vos.add(vo);
        }
        return vos;
    }
}
