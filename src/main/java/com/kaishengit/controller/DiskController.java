package com.kaishengit.controller;

import com.kaishengit.dto.AjaxResult;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.mapper.DiskMapper;
import com.kaishengit.pojo.Disk;
import com.kaishengit.service.DiskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by jiahao0 on 2017/2/21.
 */
@Controller
@RequestMapping("/pan")
public class DiskController {

    @Autowired
    private DiskService diskService;

    /**
     *查找文件列表
     * @param path 默认fid为0
     * @param model
     * @return
     */
    @GetMapping
    public String list(@RequestParam(required = false,defaultValue = "0") Integer path, Model model) {

        List<Disk> diskList = diskService.findByFid(path);
        model.addAttribute("diskList",diskList);
        model.addAttribute("fid",path);

        return "pan/list";

    }

    /**
     *新建文件夹
     * @param disk
     * @return
     */
    @PostMapping("/folder/new")
    @ResponseBody
    public AjaxResult saveFloder(Disk disk) {
        diskService.saveNewFloder(disk);
        return new AjaxResult(AjaxResult.SUCCESS);
    }

    @PostMapping("/upload")
    @ResponseBody
    public AjaxResult saveFile(Integer fid, MultipartFile file) {
        //可能发生文件为空
        try {
            diskService.saveNewFile(fid, file);
            return new AjaxResult(AjaxResult.SUCCESS);
        } catch (ServiceException e) {
            return new AjaxResult(AjaxResult.ERROR,e.getMessage());
        }
    }



}