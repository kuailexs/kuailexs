package com.kuailexs.mirror.ubports.web.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kuailexs.common.bean.Result;
import com.kuailexs.mirror.ubports.web.bean.Device;
import com.kuailexs.common.bean.ResultPage;
import com.kuailexs.mirror.ubports.web.service.DeviceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author ：dhl
 * @Date ：Created in 14:39 2019/8/22
 * @Description：
 * @Modified By：
 * @Version:
 */
@Controller
@RequestMapping(value = "mirror.ubports")
public class UbuntuTouchDeviceController {


    @Resource
    DeviceService deviceService;

    /**
     * 第三方适配设备 列表
     * @return
     */
    @RequestMapping(value = "device/listPage")
    public String devicePage(){
        return "mirror/ubports/device";
    }
    /**
     * 设备列表
     * @param model
     * @param device
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping(value = "device/list")
    @ResponseBody
    public ResultPage<Device> deviceList(Model model , Device device, Integer page, Integer limit){
        if(page == null || page < 1){
            page =1;
        }
        if(limit == null || limit < 1){
            limit =12;
        }
        PageHelper.startPage(page, limit,"create_date_ desc");
        List<Device> list =  deviceService.list(device);
        PageInfo<Device> pageInfo = new PageInfo<>(list);
        //返回数据
        return ResultPage.success(pageInfo.getList(),pageInfo.getTotal());
    }
    @RequestMapping(value = "device/save")
    @ResponseBody
    public Result<Device> deviceSave(Device device ){

        return Result.success(device);
    }
}
