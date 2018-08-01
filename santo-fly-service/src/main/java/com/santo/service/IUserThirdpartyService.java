package com.santo.service;

import com.baomidou.mybatisplus.service.IService;
import com.santo.entity.UserThirdparty;
import com.santo.model.ThirdPartyUser;
import com.santo.model.UserModel;

/**
 * <p>
 * 第三方用户表 服务类
 * </p>
 *
 * @author huliangjun
 * @since 2018-07-27
 */
public interface IUserThirdpartyService extends IService<UserThirdparty> {

    UserModel insertThirdPartyUser(ThirdPartyUser param, String password)throws Exception;

}
