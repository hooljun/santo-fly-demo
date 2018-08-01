package com.santo.base;

/**
 * @author huliangjun
 * @since 2018-05-03
 */
public enum PublicResultConstant {

    /*********************业务无关异常-start***********************/
    /** 成功*/
    SUCCESS("0000", "success"),

    /** 失败 */
    ERROR("9000", "操作失败"),

    /** 异常*/
    FAILED("9001", "系统错误"),

    /** 未登录/token过期 */
    UNAUTHORIZED("9002", "获取登录用户信息失败"),

    /** 失败 */
    PARAM_ERROR("9003", "参数错误"),

    /*********************业务无关异常-end***********************/


    /*********************用户相关异常 格式:10XX -start***********************/
    /** 用户名或密码错误 */
    INVALID_USERNAME_PASSWORD("1000", "用户名或密码错误"),

    /** 两次输入密码不一致  */
    INVALID_RE_PASSWORD("1001", "两次输入密码不一致"),

    /** 用户不存在 */
    INVALID_USER("1002", "用户不存在"),

    /** 参数错误-已存在 */
    INVALID_PARAM_EXIST("1003", "请求参数已存在"),

    /** 参数错误 */
    INVALID_PARAM_EMPTY("1004", "请求参数为空"),

    /** 没有权限 */
    USER_NO_PERMITION("1005", "当前用户无该接口权限"),

    /** 校验码错误 */
    VERIFY_PARAM_ERROR("1006", "校验码错误"),

    /**  校验码过期 */
    VERIFY_PARAM_PASS("1007", "校验码过期"),

    /** 用户没有添加、删除评论或回复的权限 */
    USER_NO_AUTHORITY("1008","该用户没有权限"),

    /** 用户没有添加、删除评论或回复的权限 */
    MOBILE_ERROR("1009","手机号格式错误") ,

    /** 数据更新或增加失败 */
    DATA_ERROR("1010","数据操作错误"),

    /*********************用户相关异常 格式:10XX -end***********************/


    /*********************角色相关异常 格式:11XX -start***********************/
    /** 角色不存在 */
    INVALID_ROLE("1100", "角色不存在"),

    /** 角色不存在 */
    ROLE_USER_USED("1101", "角色使用中，不可删除"),
    /*********************角色相关异常 格式:11XX -end***********************/
    ;

    public String result;
    public String msg;

    PublicResultConstant(String result, String msg) {
        this.result = result;
        this.msg = msg;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
