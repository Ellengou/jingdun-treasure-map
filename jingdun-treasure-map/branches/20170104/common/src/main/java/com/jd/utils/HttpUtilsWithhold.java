package com.jd.utils;

import static org.apache.commons.httpclient.params.HttpMethodParams.SO_TIMEOUT;

import java.util.Map;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;


public class HttpUtilsWithhold {
    static final Logger logger = Logger.getLogger(HttpUtils.class);

    private static final String HTTP_CONTENT_CHARSET = "utf-8";
    
    //超时3秒
    public static final Integer MAX_TIME_OUT = 16000;
    public static final Integer MAX_IDLE_TIME_OUT = 60000;
    public static final Integer MAX_CONN = 100;
    public static HttpClient httpClient = null;
    
    
    static {
        MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
        connectionManager.closeIdleConnections(MAX_IDLE_TIME_OUT);
        connectionManager.getParams().setParameter(
                "http.connection-manager.max-total", MAX_CONN);
        httpClient = new HttpClient(connectionManager);
        httpClient.getParams().setParameter(SO_TIMEOUT,
                MAX_TIME_OUT);
        httpClient.getParams().setParameter("http.connection.timeout",
                MAX_TIME_OUT);
        httpClient.getParams().setParameter("http.connection-manager.timeout",
                MAX_TIME_OUT.longValue());
    }
  

    /**
     * @param param
     * @param url
     * @return
     */
    public static String postHttp(Map<String, Object> param, String url) {

        // HTTP方法，还有get,head,put,delete,connect,options（询问可用的选项）
        PostMethod post = new PostMethod(url);

        post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,
                HTTP_CONTENT_CHARSET);
        if (param != null) {
            for (Map.Entry<String, Object> entry : param.entrySet()) {
                // 排除掉空值
                if (entry.getValue() != null) {
                    post.addParameter(entry.getKey(), entry.getValue().toString());
                }
            }
        }
        try {
            post.addRequestHeader(new Header("Connection", "close"));
            // post.addRequestHeader("Cookie", "ID=1"); //添加Cookie
            httpClient.executeMethod(post);

            if (post.getStatusCode() == HttpStatus.SC_OK) {

                return post.getResponseBodyAsString();


            } else {
                post.abort();

            }
        } catch (Exception e) {
            logger.error("http请求出错！" + url, e);

        } finally {
            post.releaseConnection();
        }

        return null;
    }
    
    
    public static String postHttpSetAva(Map<String, Object> param, String url,Integer timeOut ) {

        // HTTP方法，还有get,head,put,delete,connect,options（询问可用的选项）
        System.out.print("改变采纳数");
        
        httpClient.getParams().setParameter(SO_TIMEOUT,
                                            timeOut);
        httpClient.getParams().setParameter("http.connection.timeout",
                                            timeOut);
        httpClient.getParams().setParameter("http.connection-manager.timeout",
                                            timeOut.longValue());
        PostMethod post = new PostMethod(url);

        post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,
                HTTP_CONTENT_CHARSET);
        if (param != null) {
            for (Map.Entry<String, Object> entry : param.entrySet()) {
                // 排除掉空值
                if (entry.getValue() != null) {
                    post.addParameter(entry.getKey(), entry.getValue().toString());
                }
            }
        }
        try {
            post.addRequestHeader(new Header("Connection", "close"));
            // post.addRequestHeader("Cookie", "ID=1"); //添加Cookie
            httpClient.executeMethod(post);

            if (post.getStatusCode() == HttpStatus.SC_OK) {

                return post.getResponseBodyAsString();


            } else {
                post.abort();

            }
        } catch (Exception e) {
            logger.error("http请求出错！" + url, e);

        } finally {
            post.releaseConnection();
        }

        return null;
    }
}
