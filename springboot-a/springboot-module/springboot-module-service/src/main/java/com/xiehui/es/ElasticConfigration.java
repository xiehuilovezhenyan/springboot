package com.xiehui.es;

import java.net.InetAddress;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

@Configuration
public class ElasticConfigration {

    private String esHost = "127.0.0.1";

    private int esPort = 9300;

    private String esClusterName = "my-application";

    private TransportClient client;

    @PostConstruct
    public void initialize() throws Exception {
        Settings esSettings =
            Settings.builder().put("cluster.name", esClusterName).put("client.transport.sniff", true).build();
        client = new PreBuiltTransportClient(esSettings);

        String[] esHosts = esHost.trim().split(",");
        for (String host : esHosts) {
            client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), esPort));
        }
    }

    @Bean
    public Client client() {
        return client;
    }

    @Bean
    public ElasticsearchTemplate elasticsearchTemplate() throws Exception {
        return new ElasticsearchTemplate(client);
    }

    @PreDestroy
    public void destroy() {
        if (client != null) {
            client.close();
        }
    }

}
