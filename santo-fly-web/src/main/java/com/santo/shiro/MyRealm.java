package com.santo.shiro;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.santo.base.Constant;
import com.santo.config.SpringContextBean;
import com.santo.entity.Menu;
import com.santo.entity.Role;
import com.santo.exception.UnauthorizedException;
import com.santo.entity.User;
import com.santo.entity.UserToRole;
import com.santo.model.MenuModel;
import com.santo.service.IMenuService;
import com.santo.service.IRoleService;
import com.santo.service.IUserService;
import com.santo.service.IUserToRoleService;
import com.santo.util.ComUtil;
import com.santo.util.JWTUtil;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author huliangjun
 * @since 2018-05-03
 */
public class MyRealm extends AuthorizingRealm {
    private Logger logger = LoggerFactory.getLogger(MyRealm.class);
    private IUserService userService;
    private IUserToRoleService userToRoleService;
    private IMenuService menuService;
    private IRoleService roleService;
    /**
     * 大坑！，必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        if (userToRoleService == null) {
            this.userToRoleService = SpringContextBean.getBean(IUserToRoleService.class);
        }
        if (menuService == null) {
            this.menuService = SpringContextBean.getBean(IMenuService.class);
        }
        if (roleService == null) {
            this.roleService = SpringContextBean.getBean(IRoleService.class);
        }

        String userNo = JWTUtil.getUserNo(principals.toString());
        User user = userService.selectById(userNo);
        UserToRole userToRole = userToRoleService.selectByUserNo(user.getUserNo());

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        Set<String> roleNameSet = new HashSet<>();
        Role role = roleService.selectOne(new EntityWrapper<Role>().eq("role_code", userToRole.getRoleCode()));
        roleNameSet.add(role.getRoleName());
        //添加控制角色级别的权限
        simpleAuthorizationInfo.addRoles(roleNameSet);
        /**/
        //控制菜单级别按钮  类中用@RequiresPermissions("user:list") 对应数据库中code字段来控制controller
        ArrayList<String> pers = new ArrayList<>();
        List<MenuModel> menuList = menuService.findMenuByRoleCode(userToRole.getRoleCode());
        for (MenuModel per : menuList) {
             if (!ComUtil.isEmpty(per.getCode())) {
                  pers.add(String.valueOf(per.getCode()));
              }
        }
        Set<String> permission = new HashSet<>(pers);
        simpleAuthorizationInfo.addStringPermissions(permission);


        return simpleAuthorizationInfo;
    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws UnauthorizedException {
        if (userService == null) {
            this.userService = SpringContextBean.getBean(IUserService.class);
        }
        String token = (String) auth.getCredentials();
        if(Constant.isPass){
            return new SimpleAuthenticationInfo(token, token, this.getName());
        }
        // 解密获得username，用于和数据库进行对比
        String userNo = JWTUtil.getUserNo(token);
        if (userNo == null) {
            throw new UnauthorizedException("token invalid");
        }
        User userBean = userService.selectById(userNo);
        if (userBean == null) {
            throw new UnauthorizedException("User didn't existed!");
        }
        if (! JWTUtil.verify(token, userNo, userBean.getPassWord())) {
            throw new UnauthorizedException("Username or password error");
        }
        return new SimpleAuthenticationInfo(token, token, this.getName());
    }
}
