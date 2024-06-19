package com.neuedu.boot002.service;

import com.neuedu.boot002.pojo.User;
import com.neuedu.boot002.util.ServeResponse;

public interface IuserService {

    ServeResponse getAll(Integer pageNumber, Integer pageSize, String username, Integer roleName, Integer status);

    ServeResponse updateUserStatus(Integer id, Boolean status);

    ServeResponse likeName(String username);

    ServeResponse checkUsername(Integer id, String username);

     ServeResponse edit(User user);

    ServeResponse login(User user);
}
