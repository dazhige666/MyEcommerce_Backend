package com.neuedu.boot002.service;

import com.neuedu.boot002.pojo.Category;
import com.neuedu.boot002.util.ServeResponse;

public interface IcategoryService {
    ServeResponse getAll(Integer pageNumber, Integer pageSize, String name, Integer status);

    ServeResponse updateCateStatus(Integer id, Boolean status);

    ServeResponse checkCateName(String name, Integer id);


    ServeResponse cateEdit(Category category);
}
