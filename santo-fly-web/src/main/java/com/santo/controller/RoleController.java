package com.santo.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.santo.base.Constant;
import com.santo.base.PageResult;
import com.santo.base.PublicResult;
import com.santo.base.PublicResultConstant;
import com.santo.entity.Role;
import com.santo.entity.UserToRole;
import com.santo.model.RoleModel;
import com.santo.service.IRoleService;
import com.santo.service.IUserToRoleService;
import com.santo.util.ComUtil;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author huliangjun
 * @since 2018-05-03
 */
@RestController
@RequestMapping("/role")
@Api(description="角色管理")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IUserToRoleService userToRoleService;


    /**
     *  角色列表
     */
    @GetMapping("/pageList")
    @RequiresRoles({Constant.RoleType.SYS_ASMIN_ROLE})
    public  PublicResult getPageList(@RequestParam(name = "pageIndex", defaultValue = "1", required = false) Integer pageIndex,
                                     @RequestParam(name = "pageSize", defaultValue = "10", required = false) Integer pageSize){
        Page<Role> rolePage = roleService.selectPage(new Page<>(pageIndex, pageSize));
        //根据姓名查分页
//        Page<Role> rolePage = roleService.selectPage(new Page<>(pageIndex, pageSize),
//                new EntityWrapper<Role>().where("role_name like {0}","%"+name+"%"));
        return new PublicResult(PublicResultConstant.SUCCESS,new PageResult<>(rolePage.getTotal(),pageIndex,pageSize,rolePage.getRecords()));
    }

    /**
     *  获取所有角色
     */
    @GetMapping("/all")
    @RequiresRoles({Constant.RoleType.SYS_ASMIN_ROLE})

    public  PublicResult getAllRole(){
        List<Role> roleList = roleService.selectList(new EntityWrapper<Role>());
        return new PublicResult(PublicResultConstant.SUCCESS,roleList);
    }

    /**
     * 获取角色详细信息
     */
    @GetMapping(value = "/{roleCode}")
    @RequiresRoles({Constant.RoleType.SYS_ASMIN_ROLE})
    public PublicResult getById(@PathVariable("roleCode") String roleCode){
        Role role = roleService.selectById(roleCode);
        if(ComUtil.isEmpty(role)){
            return new PublicResult(PublicResultConstant.INVALID_ROLE,null);
        }
        Map<String, Object> result = new HashMap<>();
        result.put("role", role);
        //权限信息
        result.put("nodes", roleService.getMenuByRoleCode(role.getRoleCode()));
        return new PublicResult<>(PublicResultConstant.SUCCESS, result);
    }

    /**
     * 删除角色
     */
    @DeleteMapping(value = "/{roleCode}")
    @RequiresRoles({Constant.RoleType.SYS_ASMIN_ROLE})
    public PublicResult deleteRole(@PathVariable("roleCode") String roleCode){
        if (ComUtil.isEmpty(roleService.selectById(roleCode))) {
            return new PublicResult<>(PublicResultConstant.INVALID_ROLE, null);
        }
        if(!ComUtil.isEmpty(userToRoleService.selectList(new EntityWrapper<UserToRole>().eq("role_code",roleCode)))){
            return new PublicResult<>("角色存在相关用户,请先删除相关角色的用户", null);
        }
        boolean result = roleService.delete(new EntityWrapper<Role>().eq("role_code",roleCode));
        return result?new PublicResult<>(PublicResultConstant.SUCCESS, null): new PublicResult<>(PublicResultConstant.ERROR, null);
    }

    /**
     * 添加角色
     * @param roleModel
     * @return
     */
    @PostMapping
    @RequiresRoles({Constant.RoleType.SYS_ASMIN_ROLE})
    public PublicResult<String> addRole(RoleModel roleModel) throws Exception{
        boolean result = roleService.addRoleAndPermission(roleModel);
        return result?new PublicResult<>(PublicResultConstant.SUCCESS, null):new PublicResult<>(PublicResultConstant.INVALID_USER, null);
    }

    /**
     * 修改角色信息
     */
    @PutMapping
    @RequiresRoles({Constant.RoleType.SYS_ASMIN_ROLE})
    public PublicResult<String> updateRole(RoleModel roleModel) throws Exception{
        if (roleModel.getRoleCode().equals(
                roleService.selectOne(new EntityWrapper<Role>().eq("role_name",Constant.RoleType.SYS_ASMIN_ROLE)).getRoleCode())){
            return new PublicResult<>("超级管理员权限不可修改", null);
        }
        boolean result = roleService.updateRoleInfo(roleModel);
        return !result?new PublicResult<>(PublicResultConstant.INVALID_ROLE, null): new PublicResult<>(PublicResultConstant.SUCCESS, null);
    }



}

