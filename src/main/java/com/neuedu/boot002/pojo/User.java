package com.neuedu.boot002.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
//自动生成setter,getter
@TableName("s_user")
//表名
public class User {
    @TableId(value = "id",type = IdType.AUTO)
    //id对应表中主键。并且使其自增
    private Integer id;
    private String username;
    private String password;
    private String phone;
    private String email;
    private Integer role;
    private Integer status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("create_time")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("update_time")
    private LocalDateTime updateTime;




}
