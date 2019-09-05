package com.kuailexs.translate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * @Author ：dhl
 * @Date ：Created in 17:37 2019/8/22
 * @Description：
 * @Modified By：
 * @Version:
 */
public class TranslateGoogle {
    public final static String ZH = "zh-CN";

    public static String translate(String text, String targetLang) throws Exception {
        return execute(text,  targetLang) ;
    }

    private static String execute(final String text,
                           final String targetLang) throws Exception {
        //inspection(srcLang, targetLang) ;
        StringBuffer sb = new StringBuffer();
        StringBuilder result=new StringBuilder();
        sb.append("https://translate.google.cn/translate_a/single?").append("client=t&sl=auto")
                .append("&tl=").append(targetLang).append("&hl=zh-CN").append("&dt=at&dt=bd&dt=ex&dt=ld&dt=md&dt=qca&dt=rw")
                .append("&dt=rm&dt=ss&dt=t&ie=UTF-8&oe=UTF-8&otf=2&ssel=0&tsel=0&kc=1&tk=").append(googleToken(text)).append("&q=")
                .append(URLEncoder.encode(text, "utf-8"));

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // 创建Get请求
        HttpGet httpGet = new HttpGet(sb.toString());

        // 响应模型
        CloseableHttpResponse response = null;
        try {
            // 由客户端执行(发送)Get请求
            response = httpClient.execute(httpGet);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            System.out.println("响应状态为:" + response.getStatusLine());
            if (responseEntity != null) {
                System.out.println("响应内容长度为:" + responseEntity.getContentLength());
                String  resp = EntityUtils.toString(responseEntity);
                JSONArray jsonArray = JSON.parseArray(resp);
                jsonArray = jsonArray.getJSONArray(0);
                for(int i = 0 ; i < jsonArray.size() ;i++){
                    JSONArray jsonArray1 = jsonArray.getJSONArray(i);
                    if(jsonArray1.get(0) !=null){
                        result.append(jsonArray1.get(0));
                    }
                }
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result.toString();
    }

    private static String googleToken(String text) {
        String tk = "";
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("js");
        try {
            //FileReader reader = new FileReader(ConfigUtil.getString("GoogleJs"));
            //URL resource = TranslateGoogle.class.getResource("");
            Resource resource = new DefaultResourceLoader().getResource("classpath:Google.js");
            FileReader reader = new FileReader(new File(resource.getURI()));
            engine.eval(reader);
            if (engine instanceof Invocable) {
                Invocable invoke = (Invocable)engine;
                tk = String.valueOf(invoke.invokeFunction("token", text));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tk;
    }

    @Test
    public void bb() throws Exception {
        //String bb = googleToken("Alfred reported that the Sony Xperia X p");
       translate("Alfred reported that the Sony Xperia X port now has some sound issues caused by new components in Halium - for example, miniaudioflinger. Calling is also still an issue. Several devices have met very similar obstacles. Ofono is what we use at the moment for calling functions and we need to fix a number of bugs with that. Some other progress with Xperia X has been exported to other 7.1 devices, so a number of them are advancing together.","zh-CN");
    }
}
