package com.jd.core.context;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ylc on 2015/9/14.
 */
public class ZookeeperMonitorRegister {

    private Logger logger = LoggerFactory.getLogger(ZookeeperMonitorRegister.class);

    /**
     * zookeeper 地址(多个地址中间以,分隔)
     */
    public String hosts;
    /**
     * zookeeper 注册地址前缀 以 /开头
     */
    public String prefix;

    /**
     * 默认为本机IP，如果多网卡建议配置
     */
    public String ip;

    /**
     * 默认为true会启动客户端注册，false关闭注册
     */
    public boolean enable = true;

    /**
     * zookeeper 连接对象
     */
    private CuratorFramework client;

    /**
     * 初始方法
     */
    public void init() throws Exception {

        if (!enable) {
            logger.info("ZookeeperMonitorRegister skip");
            return;
        }
        logger.info("ZookeeperMonitorRegister init client");
        client = CuratorFrameworkFactory.builder().connectString(hosts).sessionTimeoutMs(5000)
                .connectionTimeoutMs(3000).retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();
        logger.info("ZookeeperMonitorRegister init listener");
        client.getConnectionStateListenable().addListener(new SessionConnectionStateListener(prefix, ip));
        client.start();

    }

    /**
     * 销毁方法
     */
    public void destory() {
        client.close();
    }

    public String getHosts() {
        return hosts;
    }

    public void setHosts(String hosts) {
        this.hosts = hosts;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
