package com.kuailexs.iptv.service;

import com.kuailexs.iptv.bean.IptvOne;
import com.kuailexs.iptv.bean.view.SaveResult;
import com.kuailexs.iptv.dao.IptvMapper;
import com.kuailexs.iptv.utils.IptvUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@Service
public class IptvService {
    @Resource
    IptvMapper iptvMapper;

    public SaveResult saveFromM3u8(String file) throws Exception {
        Set<IptvOne> list = IptvUtils.getFileM3u8(file);
        return saveIptvSet(list);
    }

    public SaveResult saveFromM3u8(MultipartFile file) throws Exception {
        Set<IptvOne> list = IptvUtils.getFileM3u8(file.getInputStream());
        return saveIptvSet(list);
    }

    /**
     * 实际保存
     * @param list
     * @return
     */
    public SaveResult saveIptvSet(Set<IptvOne> list) {
        SaveResult saveResult = new SaveResult();
        //保存
        for (IptvOne IptvOne : list) {
            // Integer type;// 1,http 2,其它
            // Integer status;// 0,待检查，1有效，-1 无效
            // Integer statusDesc;// 描述
            if(IptvOne.getUrl().startsWith("http")){
                IptvOne.setType(1);
            }else {
                IptvOne.setType(2);
            }
            int count = iptvMapper.selectCount(new IptvOne(IptvOne.getUrl()));
            if(count > 0){
                saveResult.setDuplicate(saveResult.getDuplicate() + 1);
            }else {
                try {
                    iptvMapper.insertSelective(IptvOne);
                    saveResult.setSuccess(saveResult.getSuccess() + 1);
                }catch (Exception e){
                    saveResult.setError(saveResult.getError() + 1);
                }
            }
        }
        saveResult.setStatus(1);
        return saveResult;
    }

    public SaveResult saveFromM3u8(MultipartFile[] file) throws Exception {
        SaveResult saveResult = new SaveResult();
        for (MultipartFile multipartFile : file) {
            SaveResult saveResult1 = saveFromM3u8(multipartFile);
            saveResult.addSaveResult(saveResult1);
        }
        return saveResult;
    }

    /**
     * 查询有效的记录
     * @return
     */
    public List<IptvOne> findAvailableList() {
        Example example = new Example(IptvOne.class);
        example.createCriteria()
                .andEqualTo("status",1);
        example.setOrderByClause("score desc");//分数倒序
        return iptvMapper.selectByExample(example);
    }
}
