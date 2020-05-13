package com.xiehui.es;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestServiceImpl {

    @Autowired
    private ESUserInfoRepository eSUserInfoRepository;

    public UserInfo queryUserInfoById(String id) {
        return eSUserInfoRepository.findById(id).orElse(null);
    }

    public UserInfo queryUserInfoByUserName(String userName) {
        return eSUserInfoRepository.findByUserName(userName);
    }

    public void save(String id) {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(Long.valueOf(id));
        userInfo.setEmail("123@qq.com");
        userInfo.setUserName("xiehui");
        userInfo.setRemark(userInfo.getRemark() + userInfo.getEmail() + id);
        eSUserInfoRepository.save(userInfo);
    }

}
