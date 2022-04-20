package com.blog.controller;

import com.blog.domain.TUser;
import com.blog.enums.ApiStatus;
import com.blog.service.ITUserService;
import com.blog.util.ApiModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户controller
 */
@RestController
public class UserController extends BaseController {

    @Autowired
    private ITUserService userService;

    @Value("${password.salt}")
    private String salt;

    /**
     * 查询用户资料
     */
    @GetMapping(value = "/user/info")
    public ResponseEntity<ApiModel> userInfo() {
        if (!checkUser()) {
            return ApiModel.fail(ApiStatus.NOT_LOGIN);
        }
        TUser user = userService.selectById(userId);
        if (user == null) {
            return ApiModel.fail("用户不存在");
        }
        user.setPassword(null);
        return ApiModel.success(user);
    }

    /**
     * 修改用户信息
     */
    @PostMapping(value = "/user/edit")
    public ResponseEntity<ApiModel> editUser(@RequestBody TUser userInfo) {
        if (!checkUser()) {
            return ApiModel.fail(ApiStatus.NOT_LOGIN);
        }
        if (userInfo == null) {
            return ApiModel.fail("用户信息不能为空");
        }
        if (StringUtils.isAllBlank(userInfo.getEmail(), userInfo.getIntro(), userInfo.getAvatar(), userInfo.getSex())) {
            return ApiModel.fail("请输入要修改的信息");
        }

        TUser user = userService.selectById(userId);
        if (user == null) {
            return ApiModel.fail("用户不存在");
        }
        TUser record = new TUser();
        record.setUserId(user.getUserId());
        if (StringUtils.isNotBlank(userInfo.getEmail())) {
            record.setEmail(userInfo.getEmail());
        }
        if (StringUtils.isNotBlank(userInfo.getSex())) {
            record.setSex(userInfo.getSex());
        }
        if (StringUtils.isNotBlank(userInfo.getIntro())) {
            record.setIntro(userInfo.getIntro());
        }
        if (StringUtils.isNotBlank(userInfo.getAvatar())) {
            record.setAvatar(userInfo.getAvatar());
        }
        userService.updateById(record);
        return ApiModel.success();
    }

}
