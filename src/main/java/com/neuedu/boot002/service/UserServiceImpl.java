package com.neuedu.boot002.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.neuedu.boot002.dao.UserDao;
import com.neuedu.boot002.pojo.User;
import com.neuedu.boot002.util.ServeResponse;
import com.neuedu.boot002.vo.userVo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class UserServiceImpl implements IuserService {
    @Resource
    private UserDao dao;

    @Override
    public ServeResponse getAll() {
        return ServeResponse.success("成功获取数据",vos(dao.selectList(null)));
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

    //数据处理
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
