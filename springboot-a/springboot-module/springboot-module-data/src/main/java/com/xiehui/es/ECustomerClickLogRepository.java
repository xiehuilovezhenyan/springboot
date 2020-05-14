package com.xiehui.es;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

@Component
public interface ECustomerClickLogRepository extends ElasticsearchRepository<ECustomerClickLog, Long>{

}
