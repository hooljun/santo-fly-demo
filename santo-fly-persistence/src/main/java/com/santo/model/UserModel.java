package com.santo.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author huliangjun
 * @since 2018-06-25
 */
@ApiModel(value = "用户实体")
public class UserModel implements Serializable{

    private static final long serialVersionUID = -2685788130281621062L;

    public UserModel(){}

    /**
     * 用户主键
     */
    @ApiModelProperty(value = "用户主键" ,required=true)
    private String userNo;
    /**
     * 是电话号码，也是账号（登录用）
     */
    @ApiModelProperty(value = "手机号码，也是账号（登录用）" ,required=true)
    private String mobile;
    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名" ,required=true)
    private String userName;
    /**
     * 密码
     */
    @ApiModelProperty(value = "密码" ,hidden = true)
    private String passWord;
    /**
     * 确认密码
     */
    @ApiModelProperty(value = "确认密码" ,hidden = true)
    private String rePassWord;
    /**
     * 单位
     */
    @ApiModelProperty(value = "单位" ,required=true)
    private String unit;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间" ,hidden = true)
    private Long createTime;
    /**
     * 头像
     */
    @ApiModelProperty(value = "头像" ,required = false)
    private String avatar;
    /**
     * 状态值（1：启用，2：禁用，3：删除）
     */
    @ApiModelProperty(value = "状态值（1：启用，2：禁用，3：删除）" ,required = true)
    private Integer status;
    /**
     * 职位
     */
    @ApiModelProperty(value = "职位" ,required = false)
    private String job;

    @ApiModelProperty(value = "认证token" ,required = true)
    private String token;

    /**
     * 验证码
     */
    @ApiModelProperty(value = "验证码" ,hidden = true)
    private String captcha;

    @ApiModelProperty(value = "角色" ,required = false)
    private String roleName;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getRePassWord() {
        return rePassWord;
    }

    public void setRePassWord(String rePassWord) {
        this.rePassWord = rePassWord;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    @Override
    public String toString() {
        return "User{" +
                "userNo=" + userNo +
                ", mobile=" + mobile +
                ", userName=" + userName +
                ", passWord=" + passWord +
                ", rePassWord=" + rePassWord +
                ", unit=" + unit +
                ", createTime=" + createTime +
                ", avatar=" + avatar +
                ", status=" + status +
                ", job=" + job +
                ", captcha=" + captcha +
                "}";
    }
}
