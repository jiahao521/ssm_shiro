package com.kaishengit.mapper;

import com.kaishengit.pojo.Disk;

import java.util.List;

/**
 * Created by jiahao0 on 2017/2/21.
 */
public interface DiskMapper {
    List<Disk> findByFid(Integer fid);

    void save(Disk disk);
}
