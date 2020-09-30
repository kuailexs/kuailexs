package com.kuailexs.iptv.utils;

import com.kuailexs.iptv.bean.IptvOne;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
@Slf4j
public class IptvUtils {

    public static Set<IptvOne> getFileM3u8(String file) throws Exception {
        return getFileM3u8(new FileInputStream(file));
    }
    public static Set<IptvOne> getFileM3u8(InputStream file) throws Exception {
        Set<IptvOne> list = new HashSet<>();

        BufferedReader br=new BufferedReader(new InputStreamReader(file,"utf-8"));
        //SpringApplication.run(IptvApplication.class, args);
        String line = null;
        IptvOne one = null;
        while ( ( line = br.readLine() ) != null){
            line = line.trim();
            if(line.length()  > 0){
                if(line.startsWith("#EXTINF")){
                    one = new IptvOne();
                    one.setInfo(line);
                }else{
                    if(one != null && one.getInfo() != null){
                        one.setUrl(line);
                        list.add(one);
                    }
                }
            }
        }
        return list;
    }
    public static boolean testAble(String url) {
        Boolean isHttps = false;
        if(url.startsWith("https")){
            isHttps = true;
        }
        HttpTools httpTools = new HttpTools(isHttps);
        try {
            String str = httpTools.executeGet(url, null, "utf-8");
            return true;
        }catch (Exception e){
            //e.printStackTrace();
            log.info("检查异常：{}",e.getMessage());
        }
        return false;
    }
}
