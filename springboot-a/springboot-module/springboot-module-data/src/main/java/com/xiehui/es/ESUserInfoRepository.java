package com.xiehui.es;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

@Component
public interface ESUserInfoRepository extends ElasticsearchRepository<UserInfo, Long> {

    UserInfo findByUserName(String userName);

}