package com.xiehui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xiehui.common.core.annotation.Access;
import com.xiehui.es.TestServiceImpl;
import com.xiehui.es.UserInfo;

@RestController
@RequestMapping("/api/v1/es")
public class ESController {
    @Autowired
    private TestServiceImpl testService;

    @RequestMapping("/es/{id}")
    @Access(isLogin = false, isSign = false, isTimestamp = false)
    public UserInfo queryAccountInfo(@PathVariable("id") String id) {
        return testService.queryUserInfoById(id);
    }

    @RequestMapping("/es/query/{userName}")
    @Access(isLogin = false, isSign = false, isTimestamp = false)
    public UserInfo queryAccountInfoByAccountName(@PathVariable("userName") String userName) {
        return testService.queryUserInfoByUserName(userName);
    }

    @RequestMapping("/es/save/{id}")
    @Access(isLogin = false, isSign = false, isTimestamp = false)
    public void save(@PathVariable("id") String id) {
        testService.save(id);
    }

}
