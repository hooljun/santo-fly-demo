package com.santo.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 菜单信息
 * </p>
 *
 * @author huliangjun
 * @since 2018-06-25
 */
@ApiModel(value = "菜单或按钮实体")
public class MenuModel implements Serializable{

    private static final long serialVersionUID = 3757230245380185372L;
    /**
     * 菜单代号,规范权限标识
     */
    @ApiModelProperty(value = "菜单代号,规范权限标识" ,required=true)
    private String menuCode;
    /**
     * 父菜单主键
     */
    @ApiModelProperty(value = "父菜单主键" ,required=true)
    private Integer parentId;

    @ApiModelProperty(value = "菜单id" ,required=true)
    private Integer menuId;
    /**
     * 菜单名称
     */
    @ApiModelProperty(value = "菜单名称" ,required=true)
    private String name;
    /**
     * 菜单类型，0：菜单  1：业务按钮
     */
    @ApiModelProperty(value = "菜单类型，0：菜单  1：业务按钮" ,required=true)
    private Integer menuType;
    /**
     * 菜单的序号
     */
    @ApiModelProperty(value = "菜单的序号" ,required=true)
    private Integer num;
    /**
     * 菜单地址
     */
    @ApiModelProperty(value = "菜单地址" ,required=true)
    private String url;

    @ApiModelProperty(value = "菜单编码" ,required=true)
    private String code;

    @ApiModelProperty(value = "菜单icon" ,required=true)
    private String icon;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @ApiModelProperty(value = "子菜单集合" ,required=true)
    private List<MenuModel> childMenu;

    public List<MenuModel> getChildMenu() {
        return childMenu;
    }

    public void setChildMenu(List<MenuModel> childMenu) {
        this.childMenu = childMenu;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMenuType() {
        return menuType;
    }

    public void setMenuType(Integer menuType) {
        this.menuType = menuType;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MenuModel{");
        sb.append("menuCode='").append(menuCode).append('\'');
        sb.append(", parentId=").append(parentId);
        sb.append(", menuId=").append(menuId);
        sb.append(", name='").append(name).append('\'');
        sb.append(", menuType=").append(menuType);
        sb.append(", num=").append(num);
        sb.append(", url='").append(url).append('\'');
        sb.append(", code='").append(code).append('\'');
        sb.append(", icon='").append(icon).append('\'');
        sb.append(", childMenu=").append(childMenu);
        sb.append('}');
        return sb.toString();
    }
}
