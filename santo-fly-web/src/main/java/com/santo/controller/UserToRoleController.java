package com.santo.controller;


import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author huliangjun
 * @since 2018-05-03
 */
@RestController
@RequestMapping("/userToRole")
//不加入swagger ui里
@ApiIgnore
public class UserToRoleController {

}

