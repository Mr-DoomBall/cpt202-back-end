package com.blog.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.blog.domain.TUser;
import com.blog.mapper.TUserMapper;
import com.blog.service.ITUserService;
import org.springframework.stereotype.Service;

/**
 * 朋友关系Service业务层处理
 */
@Service
public class TUserServiceImpl extends ServiceImpl<TUserMapper, TUser> implements ITUserService {


}
