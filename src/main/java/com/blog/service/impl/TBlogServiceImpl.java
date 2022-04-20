package com.blog.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.blog.domain.TBlog;
import com.blog.mapper.TBlogMapper;
import com.blog.service.ITBlogService;
import org.springframework.stereotype.Service;

/**
 * 博客Service业务层处理
 */
@Service
public class TBlogServiceImpl extends ServiceImpl<TBlogMapper, TBlog> implements ITBlogService {

}
