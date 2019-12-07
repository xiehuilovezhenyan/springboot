package com.xiehui.config.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.List;

@Configuration
public class RestTemplateConfigure {

    @Bean
    public RestTemplate restTemplate(){
        OkHttp3ClientHttpRequestFactory http3ClientHttpRequestFactory=new OkHttp3ClientHttpRequestFactory();
        http3ClientHttpRequestFactory.setConnectTimeout(300000);
        RestTemplate restTemplate=new RestTemplate(http3ClientHttpRequestFactory);
        List<HttpMessageConverter<?>> httpMessageConverters = restTemplate.getMessageConverters();
        httpMessageConverters.stream().forEach(httpMessageConverter -> {
            if(httpMessageConverter instanceof StringHttpMessageConverter){
                StringHttpMessageConverter messageConverter = (StringHttpMessageConverter) httpMessageConverter;
                messageConverter.setDefaultCharset(Charset.forName("UTF-8"));
            }
        });
        return restTemplate;
    }
}
