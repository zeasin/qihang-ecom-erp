package com.qihang.service.sys;

import com.qihang.interfaces.sys.User;
import com.qihang.interfaces.sys.UserService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

@DubboService
@Service
public class UserServiceImpl implements UserService {
    @Override
    public User getUserById(Long id) {
        User user = new User();
        user.setId("1000");
        user.setName("admin");
        return user;
    }
}
