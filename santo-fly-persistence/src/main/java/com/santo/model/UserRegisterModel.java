package com.santo.model;

import java.io.Serializable;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author huliangjun
 * @since 2018-06-25
 */
public class UserRegisterModel implements Serializable{

    private static final long serialVersionUID = -2206411915292262223L;

    public UserRegisterModel(){}

    /**
     * 是电话号码，也是账号（登录用）
     */
    private String mobile;
    /**
     * 姓名
     */
    private String userName;
    /**
     * 密码
     */
    private String passWord;
    /**
     * 确认密码
     */
    private String rePassWord;
    /**
     * 单位
     */
    private String unit;

    /**
     * 职位
     */
    private String job;

    /**
     * 验证码
     */
    private String captcha;

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

    public String getRePassWord() {
        return rePassWord;
    }

    public void setRePassWord(String rePassWord) {
        this.rePassWord = rePassWord;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserRegisterModel{");
        sb.append("mobile='").append(mobile).append('\'');
        sb.append(", userName='").append(userName).append('\'');
        sb.append(", passWord='").append(passWord).append('\'');
        sb.append(", rePassWord='").append(rePassWord).append('\'');
        sb.append(", unit='").append(unit).append('\'');
        sb.append(", job='").append(job).append('\'');
        sb.append(", captcha='").append(captcha).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
