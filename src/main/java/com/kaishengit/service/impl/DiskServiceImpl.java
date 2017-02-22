package com.kaishengit.service.impl;

import com.kaishengit.exception.ServiceException;
import com.kaishengit.mapper.DiskMapper;
import com.kaishengit.pojo.Disk;
import com.kaishengit.service.DiskService;
import com.kaishengit.shiro.ShiroUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

/**
 * Created by jiahao0 on 2017/2/21.
 */
@Service
public class DiskServiceImpl implements DiskService {

    @Autowired
    private DiskMapper diskMapper;

    @Value("${upload.path}")
    private String savePath;

    @Override
    public List<Disk> findByFid(Integer fid) {
        return diskMapper.findByFid(fid);
    }

    @Override
    public void saveNewFloder(Disk disk) {
        disk.setCreateUser(ShiroUtil.getCurrentUserName());
        disk.setCreateTime(DateTime.now().toString("YYYY-mm-dd hh:mm"));
        disk.setType(Disk.FOLDER_TYPE);

        diskMapper.save(disk);
    }

    @Override
    @Transactional
    public void saveNewFile(Integer fid, MultipartFile file) {

        String sourceName = file.getOriginalFilename();
        String newName = UUID.randomUUID().toString();
        Long size = file.getSize();

        if(sourceName.lastIndexOf(".") != -1) {
            newName += sourceName.substring(sourceName.lastIndexOf("."));
        }

        try {
            File saveFile = new File(new File(savePath), newName);
            FileOutputStream outputStream = new FileOutputStream(saveFile);
            InputStream inputStream = file.getInputStream();
            IOUtils.copy(inputStream, outputStream);
            outputStream.flush();
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            throw new ServiceException("保存到磁盘异常",e);
        }

        //保存数据到数据库
        Disk disk = new Disk();
        disk.setFid(fid);
        disk.setSourceName(sourceName);
        disk.setName(newName);
        disk.setCreateTime(DateTime.now().toString("yyyy-mm-dd hh:mm"));
        disk.setCreateUser(ShiroUtil.getCurrentUserName());
        disk.setType(Disk.FILE_TYPE);
        disk.setSize(FileUtils.byteCountToDisplaySize(size));

        diskMapper.save(disk);
    }
}
