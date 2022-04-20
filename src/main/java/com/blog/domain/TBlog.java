package com.blog.domain;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 博客对象 t_blog
 */
@Data
@TableName("t_blog")
public class TBlog extends Model<TBlog> {
    private static final Long serialVersionUID = 1L;

    /**
     * 博客id
     */
    @TableId(value = "blog_id", type = IdType.AUTO)
    private Integer blogId;

    /**
     * 发布用户id
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 内容
     */
    private String content;

    /**
     * 图片
     */
    private String images;

    /**
     * 赞的次数
     */
    @TableField("like_num")
    private Integer likeNum;

    /**
     * 踩的次数
     */
    @TableField("dislike_num")
    private Integer dislikeNum;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 发布者信息
     */
    @TableField(exist = false)
    private  TUser user;

    @Override
    protected Serializable pkVal() {
        return null;
    }
}
