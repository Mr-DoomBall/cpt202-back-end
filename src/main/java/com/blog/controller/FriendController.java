package com.blog.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.blog.domain.TBlog;
import com.blog.domain.TFriend;
import com.blog.domain.TUser;
import com.blog.enums.ApiStatus;
import com.blog.service.ITFriendService;
import com.blog.service.ITUserService;
import com.blog.util.ApiModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 朋友Controller
 */
@RestController
public class FriendController extends BaseController {

    @Autowired
    private ITUserService userService;

    @Autowired
    private ITFriendService friendService;

    /**
     * 查询所有朋友
     */
    @GetMapping(value = "/friend/all")
    public ResponseEntity<ApiModel> all() {
        if (!checkUser()) {
            return ApiModel.fail(ApiStatus.NOT_LOGIN);
        }
        EntityWrapper<TFriend> queryEW = new EntityWrapper<>();
        queryEW.eq("user_id", userId);
        List<TFriend> list = friendService.selectList(queryEW);
        List<TUser> friends = new ArrayList<>();
        for (TFriend friend : list) {
            //查询朋友信息
            TUser user = userService.selectById(friend.getFriendId());
            if (user != null) {
                user.setPassword(null);
                user.setLoginIp(null);
                user.setLoginDate(null);
                user.setCreateTime(null);
                friends.add(user);
            }
        }
        return ApiModel.success(friends);
    }

    /**
     * 添加朋友
     */
    @PostMapping(value = "/friend/add")
    public ResponseEntity<ApiModel> addFriend(@RequestBody TUser user) {
        if (!checkUser()) {
            return ApiModel.fail(ApiStatus.NOT_LOGIN);
        }
        if (user == null || StringUtils.isBlank(user.getUserName())) {
            return ApiModel.fail("用户名不能为空");
        }
        EntityWrapper<TUser> hasEW = new EntityWrapper<>();
        hasEW.eq("user_name", user.getUserName());
        TUser chkData = userService.selectOne(hasEW);
        if (chkData == null) {
            return ApiModel.fail("用户不存在");
        }
        TFriend friendA = new TFriend();
        friendA.setUserId(userId);
        friendA.setFriendId(chkData.getUserId());
        friendService.insert(friendA);

        TFriend friendB = new TFriend();
        friendB.setUserId(chkData.getUserId());
        friendB.setFriendId(userId);
        friendService.insert(friendB);

        return ApiModel.success();
    }

    /**
     * 删除朋友
     */
    @PostMapping(value = "/friend/delete")
    public ResponseEntity<ApiModel> delFriend(@RequestBody TUser user) {
        if (!checkUser()) {
            return ApiModel.fail(ApiStatus.NOT_LOGIN);
        }
        if (user == null || user.getUserId() == null) {
            return ApiModel.fail("用户id不能为空");
        }
        EntityWrapper<TFriend> queryEW = new EntityWrapper<>();
        queryEW.eq("user_id", userId);
        queryEW.eq("friend_id", user.getUserId());
        friendService.delete(queryEW);
        return ApiModel.success();
    }

}
