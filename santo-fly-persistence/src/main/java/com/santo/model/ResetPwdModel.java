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
public class ResetPwdModel implements Serializable{


    private static final long serialVersionUID = -4832180443250583767L;

    public ResetPwdModel(){}

    /**
     * 是电话号码，也是账号（登录用）
     */
    private String mobile;
    /**
     * 密码
     */
    private String passWord;
    /**
     * 确认密码
     */
    private String rePassWord;
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

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ResetPwdModel{");
        sb.append("mobile='").append(mobile).append('\'');
        sb.append(", passWord='").append(passWord).append('\'');
        sb.append(", rePassWord='").append(rePassWord).append('\'');
        sb.append(", captcha='").append(captcha).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
