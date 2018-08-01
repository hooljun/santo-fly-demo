package com.santo.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.santo.annotation.Log;
import com.santo.annotation.Pass;
import com.santo.annotation.ValidationParam;
import com.santo.base.Constant;
import com.santo.base.PublicResult;
import com.santo.base.PublicResultConstant;
import com.santo.entity.SmsVerify;
import com.santo.entity.User;
import com.santo.model.LoginUserAndMenuModel;
import com.santo.model.ResetPwdModel;
import com.santo.model.UserModel;
import com.santo.model.UserRegisterModel;
import com.santo.service.INoticeService;
import com.santo.service.IRoleService;
import com.santo.service.ISmsVerifyService;
import com.santo.service.IUserService;
import com.santo.util.ComUtil;
import com.santo.util.SmsSendUtil;
import com.santo.util.ValidatorUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 *  登录接口
 * @author huliangjun
 * @since 2018-05-03
 */
@RestController
@Api(description="身份认证模块")
public class LoginController {
    @Autowired
    private IUserService userService;
    @Autowired
    private ISmsVerifyService smsVerifyService;
    @Autowired
    private IRoleService roleService;

    @Autowired
    private INoticeService noticeService;

    @ApiOperation(value="手机密码登录", notes="手机密码登录,不需要Authorization",produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mobile", value = "手机号码" , required = true, dataType = "string",paramType="form"),
            @ApiImplicitParam(name = "passWord", value = "用户密码" , required = true, dataType = "string",paramType="form")
    })
    @PostMapping("/login")
    @Log(description="前台密码登录接口:/login")
    @Pass
    public PublicResult<LoginUserAndMenuModel> login(@RequestParam String mobile,@RequestParam String passWord){
        if(!ValidatorUtil.checkMobileNumber(mobile)){
            return new PublicResult<>(PublicResultConstant.MOBILE_ERROR, null);
        }
        User user = userService.selectOne(new EntityWrapper<User>().where("mobile = {0} and status = 1",mobile));
        if (ComUtil.isEmpty(user) || !BCrypt.checkpw(passWord, user.getPassWord())) {
            return new PublicResult<>(PublicResultConstant.INVALID_USERNAME_PASSWORD, null);
        }
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(user,userModel);
        LoginUserAndMenuModel result = userService.getLoginUserAndMenuInfo(userModel);
        noticeService.insertByThemeNo("themeNo-cwr3fsxf233edasdfcf2s3","13888888888");
        return new PublicResult<>(PublicResultConstant.SUCCESS, result);
    }

    @ApiOperation(value="短信验证码登录", notes="短信验证码登录,不需要Authorization",produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mobile", value = "手机号码" , required = true, dataType = "string",paramType="form"),
            @ApiImplicitParam(name = "captcha", value = "短信验证码" , required = true, dataType = "string",paramType="form")
    })
    @PostMapping("/login/captcha")
    @Log(description="前台短信验证码登录接口:/login/captcha")
    @Pass
    public PublicResult<LoginUserAndMenuModel> loginBycaptcha(@RequestParam String mobile,@RequestParam String captcha){
        if(!ValidatorUtil.checkMobileNumber(mobile)){
            return new PublicResult<>(PublicResultConstant.MOBILE_ERROR, null);
        }
        UserModel userModel = userService.getUserByMobile(mobile);
        //如果不是启用的状态
        if(!ComUtil.isEmpty(userModel) && userModel.getStatus() != Constant.ENABLE){
            return new PublicResult<>("该用户已停用!", null);
        }
        List<SmsVerify> smsVerifies = smsVerifyService.getByMobileAndCaptchaAndType(mobile,captcha, SmsSendUtil.SMSType.getType(SmsSendUtil.SMSType.AUTH.name()));
        if(ComUtil.isEmpty(smsVerifies)){
            return new PublicResult<>(PublicResultConstant.VERIFY_PARAM_ERROR, null);
        }
        if(SmsSendUtil.isCaptchaPassTime(smsVerifies.get(0).getCreateTime())){
            return new PublicResult<>(PublicResultConstant.VERIFY_PARAM_PASS, null);
        }
        if (ComUtil.isEmpty(userModel)) {
            UserModel userRegister = new UserModel();
            //设置默认密码
            userRegister.setPassWord(BCrypt.hashpw("123456", BCrypt.gensalt()));
            userRegister.setMobile(mobile);
            userRegister.setUserName(mobile);
            userModel = userService.register(userRegister, Constant.RoleType.USER);
        }
        LoginUserAndMenuModel result = userService.getLoginUserAndMenuInfo(userModel);
        return new PublicResult<>(PublicResultConstant.SUCCESS, result);
    }

    @ApiOperation(value="手机验证码注册", notes="手机验证码注册,不需要Authorization",produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "用户名" , required = true, dataType = "string",paramType="form"),
            @ApiImplicitParam(name = "mobile", value = "手机号码" , required = true, dataType = "string",paramType="form"),
            @ApiImplicitParam(name = "captcha", value = "手机短信验证码" , required = true, dataType = "string",paramType="form"),
            @ApiImplicitParam(name = "passWord", value = "密码" , required = true, dataType = "string",paramType="form"),
            @ApiImplicitParam(name = "rePassWord", value = "确认密码" , required = true, dataType = "string",paramType="form"),
            @ApiImplicitParam(name = "job", value = "工作 如：java开发" , required = true, dataType = "string",paramType="form"),
            @ApiImplicitParam(name = "unit", value = "单位 如：xxx公司" , required = false, dataType = "string",paramType="form")
    })
    @PostMapping("/register")
    @Log(description="注册接口:/register")
    @Pass
    public PublicResult<String> register(@ValidationParam("userName,passWord,rePassWord,mobile,captcha,job")
                                       @ModelAttribute UserRegisterModel userRegister) {
        if(!ValidatorUtil.checkMobileNumber(userRegister.getMobile())){
            return new PublicResult<>(PublicResultConstant.MOBILE_ERROR, null);
        }
        if (!userRegister.getPassWord().equals(userRegister.getRePassWord())) {
            return new PublicResult<>(PublicResultConstant.INVALID_RE_PASSWORD, null);
        }
        List<SmsVerify> smsVerifies = smsVerifyService.getByMobileAndCaptchaAndType(userRegister.getMobile(),userRegister.getCaptcha(),
                SmsSendUtil.SMSType.getType(SmsSendUtil.SMSType.REG.name()));
        if(ComUtil.isEmpty(smsVerifies)){
            return new PublicResult<>(PublicResultConstant.VERIFY_PARAM_ERROR, null);
        }
        //验证码是否过期
        if(SmsSendUtil.isCaptchaPassTime(smsVerifies.get(0).getCreateTime())){
            return new PublicResult<>(PublicResultConstant.VERIFY_PARAM_PASS, null);
        }
        userRegister.setPassWord(BCrypt.hashpw(userRegister.getPassWord(), BCrypt.gensalt()));
        //默认注册普通用户
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userRegister,userModel);
        userService.register(userModel, Constant.RoleType.USER);
        return new PublicResult<>(PublicResultConstant.SUCCESS, null);
    }


    @ApiOperation(value="忘记密码", notes="body体参数,不需要Authorization",produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mobile", value = "手机号码" , required = true, dataType = "string",paramType="form"),
            @ApiImplicitParam(name = "captcha", value = "手机短信验证码" , required = true, dataType = "string",paramType="form"),
            @ApiImplicitParam(name = "passWord", value = "密码" , required = true, dataType = "string",paramType="form"),
            @ApiImplicitParam(name = "rePassWord", value = "确认密码" , required = true, dataType = "string",paramType="form")
    })
    @PostMapping("/forget/password")
    @Pass
    public PublicResult<String> resetPassWord (@ValidationParam("mobile,captcha,passWord,rePassWord")
                                               @ModelAttribute ResetPwdModel resetPwdModel) throws Exception{
        String mobile = resetPwdModel.getMobile();
        if(!ValidatorUtil.checkMobileNumber(mobile)){
            return new PublicResult<>(PublicResultConstant.MOBILE_ERROR, null);
        }
        if (!resetPwdModel.getPassWord().equals(resetPwdModel.getRePassWord())) {
            return new PublicResult<>(PublicResultConstant.INVALID_RE_PASSWORD, null);
        }
        UserModel userModel = userService.getUserByMobile(mobile);
        roleService.getRoleIsAdminByUserNo(userModel.getUserNo());
        if(ComUtil.isEmpty(userModel)){
            return new PublicResult<>(PublicResultConstant.INVALID_USER, null);
        }
        List<SmsVerify> smsVerifies = smsVerifyService.getByMobileAndCaptchaAndType(mobile,
                resetPwdModel.getCaptcha(), SmsSendUtil.SMSType.getType(SmsSendUtil.SMSType.FINDPASSWORD.name()));
        if(ComUtil.isEmpty(smsVerifies)){
            return new PublicResult<>(PublicResultConstant.VERIFY_PARAM_ERROR, null);
        }
        if(SmsSendUtil.isCaptchaPassTime(smsVerifies.get(0).getCreateTime())){
            return new PublicResult<>(PublicResultConstant.VERIFY_PARAM_PASS, null);
        }
        userModel.setPassWord(BCrypt.hashpw(resetPwdModel.getPassWord(),BCrypt.gensalt()));
        User user = new User();
        BeanUtils.copyProperties(userModel,user);
        userService.updateById(user);
        return  new PublicResult<>(PublicResultConstant.SUCCESS, null);
    }

    /**
     * 检查用户是否注册过
     * @param mobile
     * @return
     * @throws Exception
     */
    @GetMapping("/check/mobile")
    @Pass
    @ApiIgnore
    public PublicResult loginBycaptcha(@RequestParam("mobile") String mobile){
        UserModel user = userService.getUserByMobile(mobile);
        return new PublicResult<>(PublicResultConstant.SUCCESS, !ComUtil.isEmpty(user));
    }
}
