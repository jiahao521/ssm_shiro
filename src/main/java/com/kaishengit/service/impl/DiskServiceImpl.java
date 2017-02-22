package com.kaishengit.service.impl;

import com.google.common.collect.Lists;
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

import java.io.*;
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

    @Override
    public InputStream downLoadFile(Integer id) throws FileNotFoundException {
        Disk disk = diskMapper.findById(id);
        if(disk == null || Disk.FOLDER_TYPE.equals(disk.getType())) {
            return null;
        } else {
            FileInputStream inputStream = new FileInputStream(new File(new File(savePath),disk.getName()));
            return inputStream;
        }
    }

    @Override
    public Disk findById(Integer id) {
        return diskMapper.findById(id);
    }

    /**
     * 用递归删除文件夹
     * @param id
     */
    @Override
    @Transactional
    public void delById(Integer id) {
        Disk disk = findById(id);
        if(disk != null) {
            //判断文件类型，文件直接删除，文件夹递归删除
            if (Disk.FILE_TYPE.equals(disk.getType())) {
                //删除文件
                File file = new File(savePath, disk.getName());
                file.delete();

                //删除数据库记录
                diskMapper.delete(id);
            } else {
                //先查询出所有网盘文件
                List<Disk> diskList = diskMapper.findAll();
                //要删除的文件id列表
                List<Integer> delIdList = Lists.newArrayList();
                findDelId(diskList, delIdList, id);

                delIdList.add(id);

                //批量删除
                diskMapper.batchDel(delIdList);
            }
        }
    }

    /**
     * 递归方法
     * @param diskList
     * @param delIdList
     * @param id
     */
    private void findDelId(List<Disk> diskList, List<Integer> delIdList, Integer id) {

        for(Disk disks : diskList) {
            //判断fid是否等于id，是的话放入删除id列表
            if(disks.getFid().equals(id)) {
                delIdList.add(disks.getId());
                if(Disk.FOLDER_TYPE.equals(disks.getType())) {
                    findDelId(diskList,delIdList,disks.getId());
                } else {
                    File file = new File(savePath,disks.getName());
                    file.delete();
                }
            }
        }
    }
}
