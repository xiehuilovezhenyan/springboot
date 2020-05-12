说明:
本项目是为了自学一些实用技能知识自建项目

1: springboot项目
2: spring cloud alibaba
3: nacos 支持注册中心以及配置中心
4: sentinel dashboard  限流监控地址配置
5: redis 原生redis API支持
6: redission 分布式锁的应用  以及  延迟队列    不建议使用-->使用MQ代替
7: 雪花生成器
8: mybatis  ###支持 主从切换, 但是不支持一主多从切换, 如果要支持一主多从切换 请参考:https://blog.csdn.net/weixin_34205826/article/details/91790793
9: 自定义分表已实现
10:ElasticJob
11:mongdb
12:rocketMQ(吞吐量大,并发高)    rabbitMQ(利用私信队列-->任意时间的延迟队列:https://www.cnblogs.com/mfrank/p/11184929.html)
13:logback
14:mybatis拦截器
15: 自动打包API接口到nexus
	1: 根pom配置distributionManagement
	2: settings 参考settings.xml
	3: 根目录编译-X clean install
	4: API模块deploy(${project_loc:/springboot-module-api}      -X clean deploy)
	5: 如何升级API接口版本 1: 配置${project_loc:springboot-a} 指向根目录  2: 执行versions:set -DnewVersion=1.0.1   然后重复上面步骤
16: 布隆过滤器Guava
17: ES