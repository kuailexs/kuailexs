package com.kuailexs.iptv.controller;

import com.kuailexs.iptv.bean.IptvOne;
import com.kuailexs.iptv.bean.view.CheckStatistics;
import com.kuailexs.iptv.bean.view.SaveResult;
import com.kuailexs.iptv.service.CheckService;
import com.kuailexs.iptv.service.IptvService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.List;

@Controller
@RequestMapping("/iptv")
public class IptvController {

    @Resource
    IptvService iptvService;
    @Resource
    CheckService checkService;

    @GetMapping("/check-statistics")
    public String index(Model model){

        // 查询统计
        CheckStatistics checkStatistics = checkService.getCheckStatistics();
        model.addAttribute("checkStatistics",checkStatistics);
        return "index";
    }

    @PostMapping("/saveFromM3u8")
    public String saveFromM3u8(@RequestParam(name = "file") MultipartFile[] file, Model model) throws Exception {

        SaveResult saveResult = iptvService.saveFromM3u8(file);
        model.addAttribute("saveResult",saveResult);
        return "save_sesult";
    }
    @GetMapping("/m3u8")
    public void getM3u8(HttpServletResponse response) throws Exception {
        List<IptvOne> list =  iptvService.findAvailableList();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=iptv.m3u8");
        OutputStream outputStream = response.getOutputStream();
        //header

        outputStream.write("#EXTM3U".getBytes("UTF-8"));
        outputStream.write("\n".getBytes("UTF-8"));
        outputStream.write("\n".getBytes("UTF-8"));

        for (IptvOne iptvOne : list) {
            String infor = iptvOne.getInfo();
            outputStream.write(infor.getBytes("UTF-8"));
            outputStream.write("\n".getBytes("UTF-8"));

            String url = iptvOne.getUrl();
            outputStream.write(url.getBytes("UTF-8"));
            outputStream.write("\n".getBytes("UTF-8"));
        }

    }
}
