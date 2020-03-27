package com.kuailexs.mirror.ubports.web.service.impl;

import com.kuailexs.common.mapper.MyBaseMapper;
import com.kuailexs.common.service.impl.BaseServiceImpl;
import com.kuailexs.mirror.ubports.web.bean.Device;
import com.kuailexs.mirror.ubports.web.mapper.DeviceMapper;
import com.kuailexs.mirror.ubports.web.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author ：dhl
 * @Date ：Created in 14:37 2019/8/23
 * @Description：
 * @Modified By：
 * @Version:
 */
@Service
public class DeviceServiceImpl extends BaseServiceImpl<Device,Integer> implements DeviceService {

    @Autowired
    DeviceMapper deviceMapper;

    @Override
    public MyBaseMapper<Device> getBaseMapper() {
        return deviceMapper;
    }
}
