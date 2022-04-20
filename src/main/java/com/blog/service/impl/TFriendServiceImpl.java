package com.blog.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.blog.domain.TFriend;
import com.blog.mapper.TFriendMapper;
import com.blog.service.ITFriendService;
import org.springframework.stereotype.Service;

/**
 * 用户信息Service业务层处理
 */
@Service
public class TFriendServiceImpl extends ServiceImpl<TFriendMapper, TFriend> implements ITFriendService {


}
