package com.santo.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.santo.service.IRoleToMenuService;
import com.santo.entity.RoleToMenu;
import com.santo.mapper.RoleToMenuMapper;
import com.santo.util.ComUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
public class RoleToMenuServiceImpl extends ServiceImpl<RoleToMenuMapper, RoleToMenu> implements IRoleToMenuService {

    @Override
    public List<RoleToMenu> selectByRoleCode(String roleCode) {
        EntityWrapper<RoleToMenu> ew = new EntityWrapper<>();
        ew.where("role_code={0}", roleCode);
        return this.selectList(ew);
    }

    @Override
    public boolean saveAll(String roleCode, List<String> menuCodes) {
        boolean result = true;
        if (!ComUtil.isEmpty(menuCodes)) {
            List<RoleToMenu> modelList = new ArrayList<>();
            for (String menuCode : menuCodes) {
                modelList.add(new RoleToMenu(roleCode, menuCode));
            }
            result = this.insertBatch(modelList);
        }
        return result;
    }

    @Override
    public boolean deleteAllByRoleCode(String roleCode) {
        EntityWrapper<RoleToMenu> ew = new EntityWrapper<>();
        ew.where("role_code={0}", roleCode);
        return this.delete(ew);
    }
}
