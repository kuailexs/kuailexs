package com.kuailexs.iptv.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 *  apache httpclient 工具类
 *  支持对 http https 接口调用
 *  <P>
 *      1.GET 请求
 *      2.POST 请求
 *  </P>
 */
public class HttpTools {
    private int timeOut = 10000;//指定超时时间
    private CloseableHttpClient httpClient;
    private RequestConfig requestConfig;//设置请求参数

    /**
     * 对象被创建时执行
     */
    public HttpTools(Boolean isHttps){
        httpClient = this.getHttpClient(isHttps);
        this.initRequestConfig();//初始化请求配置
    }

    /**
     * 发送post请求
     * @param url 请求地址
     * @param contentType 请求类型
     * @param encoding 编码格式
     * @param param 请求参数
     * @return
     */
    public String executePost(String url,String contentType,String encoding,String param)
    {
        HttpPost httpPost = new HttpPost(url);
        try {
            httpPost.setConfig(requestConfig);
            this.initMethodParam(httpPost, contentType, encoding);
            if(param!=null)
            {
                httpPost.setEntity(new StringEntity(param, encoding));//设置请求体
            }
            return this.execute(httpPost,httpClient);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e.getMessage(), e);
        }
        finally
        {
            httpPost.releaseConnection();
        }
    }

    /**
     * 发送get请求
     * @param url 请求地址
     * @param contentType 请求类型
     * @param encoding 编码格式
     * @return
     */
    public String executeGet(String url,String contentType,String encoding){
        HttpGet httpGet = new HttpGet(url);
        try{
            httpGet.setConfig(requestConfig);
            this.initMethodParam(httpGet, contentType, encoding);
            return this.execute(httpGet,httpClient);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage(),e);
        }finally{
            httpGet.releaseConnection();
        }
    }
    /**
     * 通用方法，用来发送post或get请求
     * @param method 请求类型
     * @param httpClient
     * @return
     * @throws RuntimeException
     * @throws IOException
     */
    private String execute(HttpUriRequest method,CloseableHttpClient httpClient) throws RuntimeException,IOException{
        CloseableHttpResponse response = null;
        try{
            response = httpClient.execute(method);
            StatusLine statusLine = response.getStatusLine();
            if(statusLine.getStatusCode() == 200) {
                return "";
                //HttpEntity entity = response.getEntity();
                //return EntityUtils.toString(entity, method.getFirstHeader(HttpHeaders.CONTENT_ENCODING).getValue());
            }else {
                throw new RuntimeException("not 200");
            }
        }catch (Exception e){
            throw new RuntimeException(e.getMessage(), e);
        }finally{
            if(response != null){
                response.close();
            }
        }
    }

    private void initMethodParam(HttpUriRequest method, String contentType, String encoding){
        if (contentType != null){
            method.setHeader(HttpHeaders.CONTENT_TYPE, contentType);
        }
        method.setHeader(HttpHeaders.CONTENT_ENCODING, encoding);
        method.setHeader(HttpHeaders.TIMEOUT, "15000");
        method.setHeader(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:50.0) Gecko/20100101 Firefox/50.0"); // 设置请求头消息User-Agent
    }

    /**
     * 设置初始值
     */
    private void initRequestConfig() {
        requestConfig=RequestConfig.custom().setSocketTimeout(timeOut).setConnectTimeout(timeOut).build();
    }

    /**
     * 获取主体类:
     * isHttps 区分https 和 https
     * @param isHttps
     * @return
     */
    private CloseableHttpClient getHttpClient(boolean isHttps) {
        CloseableHttpClient chc = null;
        try{
            if (isHttps){
                TrustManager[] trustManagers = new TrustManager[1];
                trustManagers[0] = new X509TrustManager(){
                    public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException{}
                    public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException{}
                    public X509Certificate[] getAcceptedIssuers(){
                        return null;
                    }
                };
                SSLContext sslContext = SSLContext.getInstance("TLS");
                sslContext.init(new KeyManager[0], trustManagers, new SecureRandom());
                SSLContext.setDefault(sslContext);
                sslContext.init(null, trustManagers, null);
                SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
                chc = HttpClients.custom().setSSLSocketFactory(sslsf).build();
            }else{
                chc=HttpClients.custom().build();
            }
        }catch (Exception e){
            throw new RuntimeException(e.getMessage(), e);
        }
        return chc;
    }

    /**
     * 关闭链接
     */
    public void destroy(){
        System.out.println("方法被执行");
        try{
            httpClient.close();
        }catch (IOException e){
            throw new RuntimeException(e.getMessage(),e);
        }
    }
}