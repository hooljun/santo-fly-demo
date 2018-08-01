package com.santo.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.santo.base.Constant;
import com.santo.entity.Menu;
import com.santo.mapper.MenuMapper;
import com.santo.model.MenuModel;
import com.santo.service.IMenuService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Menu> selectByIds(List<Integer> permissionIds) {
        EntityWrapper<Menu> ew = new EntityWrapper<>();
        ew.in("menu_id", permissionIds);
        return this.selectList(ew);
    }

    @Override
    public List<MenuModel> findMenuByRoleCode(String roleId) {
        List<MenuModel> menuModelList = new ArrayList<>();
        List<Menu> menuList = menuMapper.findMenuByRoleCode(roleId);
        if(!CollectionUtils.isEmpty(menuList)){
           for(Menu menu : menuList){
               MenuModel menuModel = new MenuModel();
               BeanUtils.copyProperties(menu,menuModel);
               menuModelList.add(menuModel);
           }
        }
        return menuModelList;
    }

    @Override
    public  List<MenuModel> treeMenuList(String pId, List<MenuModel> list) {
        List<MenuModel> IteratorMenuList = new ArrayList<>();
        for (MenuModel m : list) {
            if (String.valueOf(m.getParentId()).equals(pId)) {
                List<MenuModel> childMenuList = treeMenuList(String.valueOf(m.getMenuId()), list);
                m.setChildMenu(childMenuList);
                if(m.getMenuType() == Constant.TYPE_MENU){
                    IteratorMenuList.add(m);
                }
            }
        }
        return IteratorMenuList;
    }


}
