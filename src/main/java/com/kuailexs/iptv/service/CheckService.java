package com.kuailexs.iptv.service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.kuailexs.iptv.bean.view.CheckStatistics;
import com.kuailexs.iptv.bean.IptvOne;
import com.kuailexs.iptv.dao.IptvMapper;
import com.kuailexs.iptv.task.CheckThread;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@Slf4j
public class CheckService {

    private static List<CheckThread> threadList = new ArrayList<>();

    @Resource
    IptvMapper iptvMapper;

    public IptvOne getNextCheckOne(){

        PageHelper.startPage(0,1,false);
        IptvOne IptvOne = new IptvOne();
        IptvOne.setStatus(0);// 待检查
        List<IptvOne> list =  iptvMapper.select(IptvOne);
        if(list.size() > 0){
            return list.get(0);
        }else {
            return null;
        }
    }

    public CheckStatistics getCheckStatistics() {
        CheckStatistics checkStatistics = new CheckStatistics();
        IptvOne iptvOne = new IptvOne();
        //总计
        int count = iptvMapper.selectCount(iptvOne);
        checkStatistics.setTotal(count);
        //未检查
        iptvOne.setStatus(0);
        count = iptvMapper.selectCount(iptvOne);
        checkStatistics.setUnchecked(count);

        //未检查
        iptvOne.setStatus(2);
        count = iptvMapper.selectCount(iptvOne);
        checkStatistics.setUnableCheck(count);

        //已检查
        checkStatistics.setChecked( checkStatistics.getTotal() -  checkStatistics.getUnchecked() );
        //有效的
        iptvOne.setStatus(1);
        count = iptvMapper.selectCount(iptvOne);
        checkStatistics.setAvailable(count);
        //无效的
        checkStatistics.setUnavailable(checkStatistics.getChecked() - checkStatistics.getAvailable());

        //优质的
        Example example = new Example(IptvOne.class);
        example.createCriteria().andEqualTo("status",1).andGreaterThan("score",50);
        count = iptvMapper.selectCountByExample(example);
        checkStatistics.setExcellent(count);
        //不好的
        checkStatistics.setUnexcellent( checkStatistics.getAvailable() - checkStatistics.getExcellent());

        //检查中
        checkStatistics.setChecking(threadList.size());
        return checkStatistics;
    }
    //每隔0.5秒发出一个检查
    //@Scheduled(fixedRate= 500)
    public void checkIptvOne() {
        IptvOne iptvOne = getNextCheckOne();
        CheckThread checkThread = new CheckThread(iptvOne,iptvMapper);
        checkThread.start();
        threadList.add(checkThread);
        log.info("提交一个检查任务：{}", JSONObject.toJSONString(iptvOne));
    }
    //每5秒 检查  一下线程集合 ，去掉超时和完成的
    //@Scheduled(fixedRate= 5 * 1000)
    public void checkCheckThreadStatus() {
        log.info("当前有{}个检查任务", threadList.size());
        Iterator<CheckThread> iterator = threadList.iterator();
        while (iterator.hasNext()){
            CheckThread checkThread = iterator.next();
            if(!checkThread.isAlive() || checkThread.isOk()){
                log.info("检查任务结束移除：{}", JSONObject.toJSONString(checkThread.getIptvOne()));
                iterator.remove();
            }else {
                long startTime = checkThread.getStartTime();
                long nowTime = System.currentTimeMillis();
                //运行 超过60 秒的 结束
                if( (nowTime - startTime) > (1000 * 60)){
                    log.info("一个检查任务超时移除：{}", JSONObject.toJSONString(checkThread.getIptvOne()));
                    checkThread.stop();
                    iterator.remove();
                    IptvOne iptvOne = new IptvOne();
                    iptvOne.setId(checkThread.getIptvOne().getId());
                    iptvOne.setStatus(-1);// -1 无效 , 0,待检查，1有效，2.无法检查
                    iptvOne.setStatusDesc("检查超时");
                    iptvMapper.updateByPrimaryKeySelective(iptvOne);
                }
            }
        }
    }
}
