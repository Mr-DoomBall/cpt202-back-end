package com.blog.domain;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 朋友关系对象 t_friend
 */
@Data
@TableName("t_friend")
public class TFriend extends Model<TFriend> {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 朋友id
     */
    @TableField("friend_id")
    private Integer friendId;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    @Override
    protected Serializable pkVal() {
        return null;
    }
}
