package com.kuailexs.iptv.task;

import com.alibaba.fastjson.JSONObject;
import com.kuailexs.iptv.bean.IptvOne;
import com.kuailexs.iptv.dao.IptvMapper;
import com.kuailexs.iptv.utils.IptvUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CheckThread extends Thread {

    private long startTime ;
    private IptvOne iptvOne ;
    private boolean isOk ;
    private IptvMapper iptvMapper;
    public CheckThread(IptvOne iptvOne,IptvMapper iptvMapper) {
        super();
        this.iptvOne = iptvOne;
        this.isOk = false;
        this.iptvMapper = iptvMapper;
    }

    @Override
    public synchronized void start() {
        super.start();
        startTime = System.currentTimeMillis();
    }

    @Override
    public void run() {
        log.info("检查中：{}。。。", JSONObject.toJSONString(iptvOne));
        String url = iptvOne.getUrl();
        IptvOne distIptvOne = new IptvOne();
        distIptvOne.setId(iptvOne.getId());

        // Integer type;// 1,http 2,其它
        // Integer status;// -1 无效 , 0,待检查，1有效，2.无法检查

        if(Integer.valueOf(1).equals(iptvOne.getType())){
            boolean able = IptvUtils.testAble(url);
            this.isOk = true;
            if(able){
                distIptvOne.setStatus(1);
                distIptvOne.setStatusDesc("1有效");
            }else{
                distIptvOne.setStatus(-1);
                distIptvOne.setStatusDesc("无效");
            }
        }else{
            this.isOk = true;
            distIptvOne.setStatus(2);// -1 无效 , 0,待检查，1有效，2.无法检查
            distIptvOne.setStatusDesc("无法检查");
        }
        this.isOk = true;
        iptvMapper.updateByPrimaryKeySelective(distIptvOne);
    }

    public long getStartTime() {
        return startTime;
    }

    public IptvOne getIptvOne() {
        return iptvOne;
    }

    public boolean isOk() {
        return isOk;
    }
}
