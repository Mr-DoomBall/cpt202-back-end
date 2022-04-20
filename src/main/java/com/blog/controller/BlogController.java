package com.blog.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.blog.domain.TBlog;
import com.blog.domain.TUser;
import com.blog.enums.ApiStatus;
import com.blog.service.ITBlogService;
import com.blog.service.ITUserService;
import com.blog.util.ApiModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 博客Controller
 */
@RestController
public class BlogController extends BaseController {

    @Autowired
    private ITUserService userService;

    @Autowired
    private ITBlogService blogService;

    /**
     * 查询所有博客
     */
    @GetMapping(value = "/blog/all")
    public ResponseEntity<ApiModel> all() {
        if (!checkUser()) {
            return ApiModel.fail(ApiStatus.NOT_LOGIN);
        }
        EntityWrapper<TBlog> queryEW = new EntityWrapper<>();
        List<TBlog> list = blogService.selectList(queryEW);
        for (TBlog blog : list) {
            //查询发布者信息
            TUser user = userService.selectById(blog.getUserId());
            if (user != null) {
                user.setPassword(null);
                user.setLoginIp(null);
                user.setLoginDate(null);
                user.setCreateTime(null);
                blog.setUser(user);
            }
        }

        return ApiModel.success(list);
    }

    /**
     * 新增博客
     */
    @PostMapping(value = "/blog/add")
    public ResponseEntity<ApiModel> addBlog(@RequestBody TBlog blog) {
        if (!checkUser()) {
            return ApiModel.fail(ApiStatus.NOT_LOGIN);
        }
        if (blog == null) {
            return ApiModel.fail("博客信息不能为空");
        }
        if (StringUtils.isBlank(blog.getContent())) {
            return ApiModel.fail("请输入内容");
        }
        if (StringUtils.isBlank(blog.getImages())) {
            return ApiModel.fail("请上传图片");
        }

        blog.setUserId(userId);
        blogService.insert(blog);
        return ApiModel.success();
    }

    /**
     * 修改博客
     */
    @PostMapping(value = "/blog/edit")
    public ResponseEntity<ApiModel> editBlog(@RequestBody TBlog blog) {
        if (!checkUser()) {
            return ApiModel.fail(ApiStatus.NOT_LOGIN);
        }
        if (blog == null) {
            return ApiModel.fail("博客信息不能为空");
        }
        if (blog.getBlogId() == null || blog.getBlogId() == 0) {
            return ApiModel.fail("参数错误，blogId不能为空");
        }
        if (StringUtils.isAllBlank(blog.getContent(), blog.getImages())) {
            return ApiModel.fail("要修改的信息不能为空");
        }
        blogService.updateById(blog);
        return ApiModel.success();
    }

    /**
     * 删除博客
     */
    @PostMapping(value = "/blog/delete")
    public ResponseEntity<ApiModel> delBlog(@RequestBody TBlog blog) {
        if (!checkUser()) {
            return ApiModel.fail(ApiStatus.NOT_LOGIN);
        }
        if (blog == null) {
            return ApiModel.fail("博客信息不能为空");
        }
        if (blog.getBlogId() == null || blog.getBlogId() == 0) {
            return ApiModel.fail("参数错误，blogId不能为空");
        }
        blogService.deleteById(blog.getBlogId());
        return ApiModel.success();
    }

}
