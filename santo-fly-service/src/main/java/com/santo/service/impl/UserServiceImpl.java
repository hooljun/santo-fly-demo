package com.santo.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.santo.base.Constant;
import com.santo.entity.Menu;
import com.santo.model.LoginUserAndMenuModel;
import com.santo.model.MenuModel;
import com.santo.model.UserModel;
import com.santo.service.IMenuService;
import com.santo.service.IUserService;
import com.santo.service.IUserToRoleService;
import com.santo.entity.User;
import com.santo.entity.UserToRole;
import com.santo.mapper.UserMapper;
import com.santo.util.GenerationSequenceUtil;
import com.santo.util.JWTUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author huliangjun
 * @since 2018-05-03
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private IUserToRoleService userToRoleService;
    @Autowired
    private IMenuService menuService;

    @Autowired
    private UserMapper mapper;

    @Override
    public UserModel getUserByUserName(String username) {
        EntityWrapper<User> ew = new EntityWrapper<>();
        ew.where("user_name={0}", username);
        User user = this.selectOne(ew);
        UserModel userModel = null;
        if(user != null){
            userModel = new UserModel();
            BeanUtils.copyProperties(user,userModel);
        }
        return userModel;
    }

    @Override
    public UserModel getUserByMobile(String mobile) {
        EntityWrapper<User> ew = new EntityWrapper<>();
        ew.eq("mobile", mobile);
        User user = this.selectOne(ew);
        UserModel userModel = null;
        if(user != null){
            userModel = new UserModel();
            BeanUtils.copyProperties(user,userModel);
        }
        return userModel;
    }

    @Override
    public UserModel register(UserModel userModel, String  roleCode) {
        User user = new User();
        BeanUtils.copyProperties(userModel, user);
        user.setUserNo(GenerationSequenceUtil.generateUUID("user"));
        user.setCreateTime(System.currentTimeMillis());
        boolean result = this.insert(user);
        if (result) {
            userModel.setUserNo(user.getUserNo());
            UserToRole userToRole  = new UserToRole();
            userToRole.setUserNo(user.getUserNo());
            userToRole.setRoleCode(roleCode);
            userToRoleService.insert(userToRole);
        }
        return userModel;
    }

    @Override
    public LoginUserAndMenuModel getLoginUserAndMenuInfo(UserModel userModel) {
        LoginUserAndMenuModel loginUserAndMenuModel = new LoginUserAndMenuModel();
        UserToRole userToRole = userToRoleService.selectByUserNo(userModel.getUserNo());
        userModel.setToken(JWTUtil.sign(userModel.getUserNo(), userModel.getPassWord()));
        userModel.setPassWord(null);
        loginUserAndMenuModel.setUserModel(userModel);
        List<MenuModel> buttonList = new ArrayList<>();
        //根据角色主键查询启用的菜单权限
        List<MenuModel> menuList = menuService.findMenuByRoleCode(userToRole.getRoleCode());
        List<MenuModel> retMenuList = menuService.treeMenuList(Constant.ROOT_MENU, menuList);
        for (MenuModel buttonMenu : menuList) {
            if(buttonMenu.getMenuType() == Constant.TYPE_BUTTON){
                buttonList.add(buttonMenu);
            }
        }
        loginUserAndMenuModel.setMenuList(retMenuList);
        loginUserAndMenuModel.setButtonList(buttonList);
        return loginUserAndMenuModel;
    }

    @Override
    public boolean deleteByUserNo(String userNo) {
        EntityWrapper<UserToRole> ew = new EntityWrapper<>();
        ew.eq("user_no", userNo);
        boolean resultRole = userToRoleService.delete(ew);
        boolean  resultUser = this.deleteById(userNo);
        return resultRole && resultUser;
    }

    @Override
    public Page<User> selectPageByConditionUser(Page<User> userPage, String info, Integer[] status, String startTime, String endTime) {
        //注意！！ 分页 total 是经过插件自动 回写 到传入 page 对象
        return userPage.setRecords(mapper.selectPageByConditionUser(userPage, info,status,startTime,endTime));
    }


}
