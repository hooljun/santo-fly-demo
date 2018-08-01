package com.santo.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel(value = "登录用户和菜单信息")
public class LoginUserAndMenuModel implements Serializable{

    @ApiModelProperty(value = "登录用户信息" ,required=true)
    private UserModel userModel;

    @ApiModelProperty(value = "菜单列表" ,required=true)
    private List<MenuModel> menuList;

    @ApiModelProperty(value = "按钮列表" ,required=true)
    private List<MenuModel> buttonList;

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public List<MenuModel> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<MenuModel> menuList) {
        this.menuList = menuList;
    }

    public List<MenuModel> getButtonList() {
        return buttonList;
    }

    public void setButtonList(List<MenuModel> buttonList) {
        this.buttonList = buttonList;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("LoginUserAndMenuModel{");
        sb.append("userModel=").append(userModel);
        sb.append(", menuList=").append(menuList);
        sb.append(", buttonList=").append(buttonList);
        sb.append('}');
        return sb.toString();
    }
}
