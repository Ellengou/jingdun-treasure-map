package com.jd.core.utils.proxy;

import com.jd.core.exceptions.XBusinessException;
import org.apache.commons.io.IOUtils;
import org.apache.http.*;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Zhenwei on 11/24/15.
 */
public class HttpClientUtils {

    private List<Header> headers;

    private HttpClientUtils() {
        headers = new ArrayList();
    }

    public static HttpClientUtils getInstance() {
        return new HttpClientUtils();
    }

    public HttpClientUtils addHeader(String key, String value) {
        headers.add(new BasicHeader(key, value));
        return this;
    }

    private String sign;

    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);

    public <T> T postJson(String url, String message, Callback<T> callback) {
        return postJson(url, message, "UTF-8", callback);
    }

    public <T> T postJson(String url, String message, String charset, Callback<T> callback) {
        return post(url, message, charset, "application/json", callback);
    }

    public <T> T postForm(String url, String message, Callback<T> callback) {
        return postForm(url, message, "UTF-8", callback);
    }

    public <T> T postForm(String url, String message, String charset, Callback<T> callback) {
        return post(url, message, charset, "application/x-www-form-urlencoded", callback);
    }

    public String postForm(String url, Map<String, String> messageMap) {
        return postForm(url, messageMap, new DefaultStringCallback());
    }

    public <T> T postForm(String url, Map<String, String> messageMap, Callback<T> callback) {
        logger.debug("start to post");
        CloseableHttpResponse response = null;
        String charset = "UTF-8";
        String contentType = "application/x-www-form-urlencoded";
        try {
            logger.debug("start to get httpclient");
            CloseableHttpClient client = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader("Content-Type", contentType);
            httpPost.addHeader("charset", charset);
            addHeaders(httpPost);

            List<NameValuePair> params = new ArrayList<>();
            for (String key : messageMap.keySet()) {
                params.add(new BasicNameValuePair(key, messageMap.get(key)));
            }
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(params, charset);
            httpPost.setEntity(urlEncodedFormEntity);

            logger.debug("start to execute");
            response = client.execute(httpPost);
            logger.debug("start to handle response");

            validateResponse(url, charset, response);

            return callback.execute(response, charset);
        } catch (Exception ex) {
            throw new XBusinessException(ex.getMessage());
        } finally {
            closeResponse(response);
        }
    }

    public <T> T postXml(String url, String message, Callback<T> callback) {
        return postXml(url, message, "UTF-8", callback);
    }

    public <T> T postXml(String url, String message, String charset, Callback<T> callback) {
        return post(url, message, charset, "application/xml", callback);
    }

    public <T> T post(String url, String message, String charset, String contentType, Callback<T> callback) {
        logger.debug("start to post");
        CloseableHttpResponse response = null;
        try {
            logger.debug("start to get httpclient");
            //CloseableHttpClient client = HttpClientFactory.getFactory().getDefaultCloseableHttpClient();
            CloseableHttpClient client = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader("Content-Type", contentType);
            httpPost.addHeader("charset", "UTF-8");
            addHeaders(httpPost);

            StringEntity strEntity = new StringEntity(message, charset);
            httpPost.setEntity(strEntity);
            logger.debug("start to execute");
            response = client.execute(httpPost);
            logger.debug("start to handle response");

            validateResponse(url, charset, response);

            return callback.execute(response, charset);
        } catch (Exception ex) {
            throw new XBusinessException(ex.getMessage());
        } finally {
            closeResponse(response);
        }
    }

    public <T> T putJson(String url, String message,  Callback<T> callback){
        return putJson(url, message, "UTF-8", callback);
    }

    public <T> T putJson(String url, String message, String charset, Callback<T> callback){
        return put(url, message, charset, "application/json", callback);
    }

    public <T> T put(String url, String message, String charset, String contentType, Callback<T> callback) {
        logger.debug("start to post");

        CloseableHttpResponse response = null;
        try{
            logger.debug("start to get httpclient");
            //CloseableHttpClient client = HttpClientFactory.getFactory().getDefaultCloseableHttpClient();
            CloseableHttpClient client = HttpClients.createDefault();
            HttpPut httpPut = new HttpPut(url);
            httpPut.addHeader("Content-Type", contentType);
            httpPut.addHeader("charset", "UTF-8");
            addHeaders(httpPut);

            StringEntity strEntity = new StringEntity(message, charset);
            httpPut.setEntity(strEntity);
            logger.debug("start to execute");
            response = client.execute(httpPut);
            logger.debug("start to handle response");

            validateResponse(url, charset, response);

            return callback.execute(response, charset);
        }catch (Exception ex){
            throw new XBusinessException(ex.getMessage());
        }finally {
            closeResponse(response);
        }
    }

    public <T> T get(String url, Callback<T> callback) {
        return get(url, "UTF-8", callback);
    }

    public <T> T get(String url, String charset, Callback<T> callback) {
        CloseableHttpResponse response = null;
        try {
            CloseableHttpClient client = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(url);
            addHeaders(httpGet);
            response = client.execute(httpGet);

            validateResponse(url, charset, response);

            return callback.execute(response, charset);
        } catch (Exception ex) {
            throw new XBusinessException(ex.getMessage());
        } finally {
            closeResponse(response);
        }
    }

    public <T> T delete(String url, Callback<T> callback){
        return delete(url, "UTF-8", callback);
    }

    public <T> T delete(String url, String charset, Callback<T> callback){
        CloseableHttpResponse response = null;
        try{
            CloseableHttpClient client = HttpClients.createDefault();
            HttpDelete httpDelete = new HttpDelete(url);
            addHeaders(httpDelete);
            response = client.execute(httpDelete);

            validateResponse(url, charset,response);

            return callback.execute(response, charset);
        }catch (Exception ex){
            throw new XBusinessException(ex.getMessage());
        }finally {
            closeResponse(response);
        }
    }

    private void validateResponse(String url, String charset, CloseableHttpResponse response) throws IOException {
        HttpEntity entity = response.getEntity();
        if (entity == null) {
            String errMsg = String.format("HTTP请求无返回数据[url=%s,charset=%s]", url, charset);
            throw new RuntimeException(errMsg);
        }

        if (HttpStatus.SC_OK != response.getStatusLine().getStatusCode()) {
            String statusCode = String.valueOf(response.getStatusLine().getStatusCode());
            InputStream in = entity.getContent();/*消耗 并关闭流*/
            in.close();
            String errMsg = String.format("HTTP请求返回异常[url=%s,charset=%s,statusCode=%s]", url, charset, statusCode);
            throw new XBusinessException(errMsg);
        }
    }

    private void closeResponse(CloseableHttpResponse response) {
        try {
            if (response != null) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    entity.getContent().close();
                }
                response.close();
            }
        } catch (IOException e) {
            logger.error("Close response failed", e);
        }
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    private void addHeaders(HttpRequestBase request) {
        for (Header header : headers) {
            request.addHeader(header);
        }
    }

    private class DefaultStringCallback implements Callback<String> {

        @Override
        public String execute(HttpResponse response, String charset) throws Exception {
            HttpEntity entity = response.getEntity();
            return IOUtils.toString(entity.getContent(), charset);
        }

    }

}
