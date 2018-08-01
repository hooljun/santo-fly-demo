package com.santo.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.santo.service.IUserToRoleService;
import com.santo.entity.UserToRole;
import com.santo.mapper.UserToRoleMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.santo.util.ComUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author huliangjun
 * @since 2018-05-03
 */
@Service
public class UserToRoleServiceImpl extends ServiceImpl<UserToRoleMapper, UserToRole> implements IUserToRoleService {

    @Override
    public UserToRole selectByUserNo(String userNo) {
        EntityWrapper<UserToRole> ew = new EntityWrapper<>();
        ew.where("user_no={0}", userNo);
        List<UserToRole> userToRoleList = this.selectList(ew);
        return ComUtil.isEmpty(userToRoleList)? null: userToRoleList.get(0);
    }
}
