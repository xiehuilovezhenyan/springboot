package com.xiehui.es;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.client.naming.utils.CollectionUtils;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.xiehui.constant.DataSourceName;
import com.xiehui.data.DCustomerClickLog;
import com.xiehui.data.DCustomerClickLogDAO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TestServiceImpl {

    @Autowired
    private ESUserInfoRepository eSUserInfoRepository;
    @Autowired
    private ECustomerClickLogRepository eCustomerClickLogRepository;
    @Autowired
    private DCustomerClickLogDAO dCustomerClickLogDAO;
    @Autowired
    private TaskExecutor taskExecutor;
    @Autowired
    private ElasticsearchTemplate esElasticsearchTemplate;

    public UserInfo queryUserInfoById(Long id) {
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

    @DS(DataSourceName.SLAVE)
    public void reloadClickLog() {

        // 初始化
        int startIndex = 0;
        int pageSize = 500;

        // 循环处理数据
        while (true) {
            // 获取分页数据
            List<DCustomerClickLog> dCustomerClickLogs = dCustomerClickLogDAO.queryAll(startIndex, pageSize);
            if (CollectionUtils.isEmpty(dCustomerClickLogs)) {
                return;
            }
            taskExecutor.execute(new Runnable() {

                @Override
                public void run() {
                    List queries = new ArrayList();
                    for (DCustomerClickLog dCustomerClickLog : dCustomerClickLogs) {
                        ECustomerClickLog eCustomerClickLog = new ECustomerClickLog();
                        BeanUtils.copyProperties(dCustomerClickLog, eCustomerClickLog);
                        log.info("eCustomerClickLog =>" + JSON.toJSONString(dCustomerClickLog));
                        // eCustomerClickLogRepository.save(eCustomerClickLog);
                        
                        IndexQuery indexQuery = new IndexQuery();
                        indexQuery.setId(eCustomerClickLog.getId().toString());
                        indexQuery.setSource(JSON.toJSONString(eCustomerClickLog));
                        indexQuery.setIndexName("customerclicklog");
                        indexQuery.setType("clickLogInfo");
                        queries.add(indexQuery);
                    }
                    esElasticsearchTemplate.bulkIndex(queries);

                }
            });

            // 检查没有更多
            if (dCustomerClickLogs.size() < pageSize) {
                break;
            }

            // 递增开始序号
            startIndex += pageSize;
        }
    }

}
