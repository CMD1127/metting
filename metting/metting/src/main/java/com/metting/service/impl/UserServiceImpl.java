package com.metting.service.impl;

import com.metting.entity.User;
import com.metting.mapper.UserMapper;
import com.metting.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wcc
 * @since 2023-08-30
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
