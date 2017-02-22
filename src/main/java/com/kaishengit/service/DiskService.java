package com.kaishengit.service;

import com.kaishengit.pojo.Disk;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by jiahao0 on 2017/2/21.
 */
public interface DiskService {
    List<Disk> findByFid(Integer path);

    void saveNewFloder(Disk disk);

    void saveNewFile(Integer fid, MultipartFile file);
}
