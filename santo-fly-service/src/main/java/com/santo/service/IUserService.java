package com.santo.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.santo.entity.User;
import com.baomidou.mybatisplus.service.IService;
import com.santo.model.LoginUserAndMenuModel;
import com.santo.model.UserModel;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author huliangjun
 * @since 2018-05-03
 */
public interface IUserService extends IService<User> {

    /**
     * 根据用户名查询用户
     * @param username 用户名
     * @return 用户
     */
    UserModel getUserByUserName(String username);

    UserModel getUserByMobile(String mobile);

    /**
     * 注册用户
     * @param user
     * @param roleCode
     * @return
     */
    UserModel register(UserModel user, String roleCode);

    LoginUserAndMenuModel getLoginUserAndMenuInfo(UserModel userModel);

    boolean deleteByUserNo(String userNo);

    Page<User> selectPageByConditionUser(Page<User> userPage, String info, Integer[] status, String startTime, String endTime);
}
