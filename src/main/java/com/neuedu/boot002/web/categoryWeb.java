package com.neuedu.boot002.web;

import com.neuedu.boot002.pojo.Category;
import com.neuedu.boot002.service.IcategoryService;
import com.neuedu.boot002.util.ServeResponse;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/back")
@CrossOrigin("*")
public class categoryWeb {

    @Resource
    private IcategoryService service;
    @RequestMapping("cate")
    public ServeResponse cate(Integer pageNumber,Integer pageSize,String name,Integer status){
        return service.getAll (pageNumber,pageSize,name,status);
    }

    @RequestMapping("updateCateStatus")
    public ServeResponse updateStatus(Integer id,Boolean status){
        return service.updateCateStatus(id,status);
    }

    @RequestMapping("checkCateName")
    public ServeResponse checkCateName(String name,Integer id){
        return service.checkCateName(name,id);
    }
    @RequestMapping("cateEdit")
    public ServeResponse CateEdit(@RequestBody Category category){
        return service.cateEdit(category);
    }


}
