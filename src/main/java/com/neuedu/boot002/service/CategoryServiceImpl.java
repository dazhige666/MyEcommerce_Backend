package com.neuedu.boot002.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.neuedu.boot002.dao.CategoryDao;
import com.neuedu.boot002.pojo.Category;
import com.neuedu.boot002.util.ServeResponse;
import com.neuedu.boot002.vo.CategoryVo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements IcategoryService{
    @Resource
    private CategoryDao dao;
    @Override
    public ServeResponse getAll(Integer pageNumber, Integer pageSize, String name, Integer status) {

        if(pageNumber==null||pageNumber<=1)
            pageNumber = 1;
        if(pageSize==null||pageSize<=0)
            pageSize = 12;

        QueryWrapper<Category> qw = new QueryWrapper<>();
        if(!(name == null||"".equals(name))){
            qw.like("name",name);
        }
        if(status!= null){
            qw.eq("status",status);
        }
        IPage page = new Page<>(pageNumber,pageSize);
        dao.selectPage(page,qw);
        page.setRecords(vos(page.getRecords()));

        long p1 = page.getTotal()%pageSize==0? page.getTotal()/pageSize:page.getTotal()/pageSize+1;
        if(pageNumber>p1)
            page.setCurrent(1);
        //如果传入的页码数大于查询出的页码数，数据会因为页码过大无法显示，所以令当前页为1
        return ServeResponse.success("成功获取数据",page);

    }
    @Override
    public ServeResponse updateCateStatus(Integer id, Boolean status) {

        Category c =  new Category();
        c.setId(id);
        c.setUpdateTime(LocalDateTime.now());
        c.setStatus(status? 1:0 );
        int i = dao.updateById(c);
        return (i==1? ServeResponse.success("修改成功"):ServeResponse.error("修改失败"));
    }

    @Override
    public ServeResponse checkCateName(String name, Integer id) {
        System.out.println(name+"          "+id);
        QueryWrapper<Category> q1 = new QueryWrapper();
        q1.eq("id",id);
        Category c1 = dao.selectOne(q1);
        if(name==null&&id==0)
            return ServeResponse.error("类别名不可为空");
            //不能添加id为0，用户名为空的用户
        else if(c1==null){
            if(dao.selectOne(new QueryWrapper<Category>().eq("name",name))==null)
                return ServeResponse.success("类别名可以使用添加");
            else return ServeResponse.error("类别名已存在");
        }
        //数据库不存在传入id的用户，此时判断是否存在相同用户名的用户
        //都不存在说明是在添加用户，且可以添加
        else if(c1.getName().equals(name))
            return ServeResponse.error("当前类别名与原类别名相同");
            //数据库存在传入id用户，且其用户名与传入的用户名相同
        else {
            QueryWrapper<Category> q2 = new QueryWrapper();
            q2.eq("name",name);
            q2.ne("id",id);
            Category c2 = dao.selectOne(q2);
            //查询数据库是否有与传入值相同的用户名，没有则可以修改
            if(c2 == null){
                return ServeResponse.success("类别名不存在，可以修改");
            }else return ServeResponse.error("类别名被占用");

        }
    }

    @Override
    public ServeResponse cateEdit(Category category) {


        System.out.println(category);
        if(category.getId()>0){
            category.setUpdateTime(LocalDateTime.now());
            int update = dao.updateById(category);
            if(update>0)
                return ServeResponse.success("修改成功");
            else return ServeResponse.error("修改失败");
        }else{
            category.setCreateTime(LocalDateTime.now());
            category.setUpdateTime(LocalDateTime.now());
            category.setStatus(1);

            if(dao.insert(category)>0)
                return ServeResponse.success("添加成功");
            else return ServeResponse.error("添加失败");
        }
    }

    public List<CategoryVo> vos(List<Category> lists){

        List<CategoryVo> vs = new ArrayList<>();
        for(Category c: lists){
            CategoryVo vo = new CategoryVo();
            vo.setId(c.getId());
            vo.setCreateTime(c.getCreateTime());
            vo.setUpdateTime(c.getUpdateTime());
            vo.setName(c.getName());
            vo.setStatus(c.getStatus()==1);
            Integer parentID = c.getParentID();
            Category cg = dao.selectById(parentID);
            if(cg!=null){
                //System.out.println(cg.getName());
                vo.setParentName(cg.getName());
            }
            else vo.setParentName("一级品类");
            vs.add(vo);
        }
        return vs;

    }
}
