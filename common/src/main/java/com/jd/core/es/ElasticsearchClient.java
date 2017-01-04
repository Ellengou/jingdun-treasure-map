package com.jd.core.es;

import org.apache.log4j.Logger;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ylc on 2015/11/5.
 */
public class ElasticsearchClient {

    private final static Logger logger = Logger.getLogger(ElasticsearchClient.class);

    private String hosts;

    private String user;

    private String password;

    private String clusterName;


    @Bean
    public TransportClient transportClient() {
        Map<String, String> settingMap = new HashMap<String, String>();
        settingMap.put("username", user);
        settingMap.put("password", password);
        settingMap.put("client.transport.sniff", "true");
        settingMap.put("cluster.name", clusterName);

        Settings settings = ImmutableSettings.settingsBuilder().put(settingMap).build();

        InetSocketTransportAddress[] addresses = getInetSocketTransportAddress(hosts);
        TransportClient client = new TransportClient(settings).addTransportAddresses(addresses);
        return client;
    }

    InetSocketTransportAddress[] getInetSocketTransportAddress(String hosts) {
        Map<String, Integer> address = new HashMap<>();
        for (String host : hosts.split(",")) {
            String[] str = host.split(":");
            address.put(str[0].trim(), Integer.parseInt(str[1].trim()));
        }
        InetSocketTransportAddress[] addresses = new InetSocketTransportAddress[address.size()];
        int i = 0;
        for (Map.Entry<String, Integer> entry : address.entrySet()) {
            addresses[i] = new InetSocketTransportAddress(entry.getKey(), entry.getValue());
            i++;
        }
        return addresses;
    }

    public String getHosts() {
        return hosts;
    }

    public void setHosts(String hosts) {
        this.hosts = hosts;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }
}
