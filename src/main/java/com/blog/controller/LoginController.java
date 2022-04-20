package com.blog.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.blog.domain.TUser;
import com.blog.service.ITUserService;
import com.blog.util.ApiModel;
import com.blog.util.SignUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 登录controller
 */
@RestController
public class LoginController extends BaseController {

    @Autowired
    private ITUserService userService;

    @Value("${password.salt}")
    private String salt;

    /**
     * 登录
     */
    @PostMapping(value = "login")
    public ResponseEntity<ApiModel> login(@RequestBody TUser loginUser, HttpServletRequest request) {
        if (loginUser == null) {
            return ApiModel.fail("登录信息不能为空");
        }

        if (StringUtils.isBlank(loginUser.getUserName())) {
            return ApiModel.fail("用户名不能为空");
        }

        if (StringUtils.isBlank(loginUser.getPassword())) {
            return ApiModel.fail("密码不能为空");
        }

        //查询用户是否存在
        EntityWrapper<TUser> hasEW = new EntityWrapper<>();
        hasEW.eq("user_name", loginUser.getUserName());
        TUser user = userService.selectOne(hasEW);
        if (user == null) {
            return ApiModel.fail("用户名不存在");
        }
        if (!SignUtil.MD5(loginUser.getPassword().concat(salt)).equals(user.getPassword())) {
            return ApiModel.fail("用户名或密码错误");
        } else {
            //登录成功
            httpSession.setAttribute("userId", user.getUserId());
            //记录登录信息
            user.setLoginDate(new Date());
            user.setLoginIp(getIpAddress(request));
            userService.updateById(user);
        }
        user.setPassword(null);
        user.setLoginIp(null);
        user.setLoginDate(null);
        user.setCreateTime(null);
        return ApiModel.success(user);

    }

    /**
     * 注册
     */
    @PostMapping(value = "register")
    public ResponseEntity<ApiModel> register(@RequestBody TUser registerUser) {
        if (registerUser == null) {
            return ApiModel.fail("注册信息不能为空");
        }

        if (StringUtils.isBlank(registerUser.getUserName())) {
            return ApiModel.fail("用户名不能为空");
        }

        if (StringUtils.isBlank(registerUser.getPassword())) {
            return ApiModel.fail("密码不能为空");
        }

        //查询用户是否存在
        EntityWrapper<TUser> hasEW = new EntityWrapper<>();
        hasEW.eq("user_name", registerUser.getUserName());
        TUser user = userService.selectOne(hasEW);
        if (user != null) {
            return ApiModel.fail("用户名重复");
        }
        registerUser.setPassword(SignUtil.MD5(registerUser.getPassword().concat(salt)));
        userService.insert(registerUser);
        return ApiModel.success();
    }

    /**
     * 修改密码
     */
    @PostMapping(value = "password/edit")
    public ResponseEntity<ApiModel> editPwd(@RequestBody TUser user) {
        if (user == null) {
            return ApiModel.fail("用户信息不能为空");
        }

        if (StringUtils.isBlank(user.getUserName())) {
            return ApiModel.fail("用户名不能为空");
        }

        if (StringUtils.isAnyBlank(user.getPassword(), user.getOriginalPassword())) {
            return ApiModel.fail("密码不能为空");
        }

        //查询用户是否存在
        EntityWrapper<TUser> hasEW = new EntityWrapper<>();
        hasEW.eq("user_name", user.getUserName());
        TUser chkUser = userService.selectOne(hasEW);
        if (chkUser == null) {
            return ApiModel.fail("用户名不存在");
        }

        if (!SignUtil.MD5(user.getOriginalPassword().concat(salt)).equals(chkUser.getPassword())) {
            return ApiModel.fail("原密码错误");
        } else {
            chkUser.setPassword(SignUtil.MD5(user.getPassword().concat(salt)));
        }
        userService.updateById(chkUser);

        return ApiModel.success();
    }

    @GetMapping(value = "test")
    public ResponseEntity<ApiModel> gest() {
        if (!checkUser()) {
            return ApiModel.fail("未登录");
        } else {
            return ApiModel.fail("已登录");
        }

    }

    public static void main(String[] args) {
        System.out.println(SignUtil.MD5("123456".concat("blog_api_pwd_salt")));
    }
}
