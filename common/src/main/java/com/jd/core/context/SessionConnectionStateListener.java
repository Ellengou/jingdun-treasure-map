package com.jd.core.context;

import org.apache.commons.lang3.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;

/**
 * Created by ylc on 2015/10/9.
 */
public class SessionConnectionStateListener implements ConnectionStateListener {

    private Logger logger = LoggerFactory.getLogger(SessionConnectionStateListener.class);

    private String prefix;

    private String ip;

    public SessionConnectionStateListener(String prefix, String ip) {
        this.prefix = prefix;
        this.ip = ip;
    }

    @Override
    public void stateChanged(CuratorFramework client, ConnectionState newState) {
        /** 当客户端第一次连接成功时触发 */
        if (ConnectionState.CONNECTED == newState) {
            try {
                register(client);
                logger.info("zookeeperMonitorRegister start success");
            } catch (Exception e) {
                logger.error("zookeeperMonitorRegister start error:" + e);
            }
        } else if (ConnectionState.LOST == newState) {
            logger.info("zookeeperMonitorRegister LOST Waiting for retry");
            while (true) {
                try {
                    if (client.getZookeeperClient().blockUntilConnectedOrTimedOut()) {
                        client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(getPath(client), getIpAddress().getBytes("UTF-8"));
                        break;
                    }
                } catch (Exception e) {
                    logger.error("zookeeperMonitorRegister LOST retry error:" + e);
                }
            }
        }
    }

    public void register(CuratorFramework client) throws Exception {
        String path = getPath(client);
        String ipAddress = getIpAddress();
        client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(path, ipAddress.getBytes("UTF-8"));
    }

    /**
     * 获取zookeeper注册路径
     *
     * @param client
     * @return
     * @throws Exception
     */
    private String getPath(CuratorFramework client) throws Exception {
        /**检查是否存在项目根节点，不存在则创建 */
        String path = this.prefix;
        if (!path.startsWith("/")) {
            path = "/" + path;
        }

        Stat stat = client.checkExists().forPath(path);
        if (stat == null) {
            client.create().creatingParentsIfNeeded().forPath(path, new byte[0]);
        }

        /**临时节点路径 */
        path += "/provider-";
        return path;
    }

    /**
     * 获取zookeeper注册内容
     *
     * @return
     * @throws Exception
     */
    private String getIpAddress() throws Exception {
        String ip = null;
        if (StringUtils.isNotBlank(ip)) {
            ip = this.ip;
        } else {
            ip = InetAddress.getLocalHost().getHostAddress().toString();
        }
        return ip;
    }
}
