package com.neuedu.boot002.service;

import com.neuedu.boot002.util.ServeResponse;
import org.springframework.web.servlet.function.ServerResponse;

public interface IuserService {
    ServeResponse getAll();
    ServeResponse updateStatus(Integer id,Boolean status);

    ServeResponse likeName(String username);
}
