package com.kaishengit.pojo;

import lombok.Data;

/**
 * Created by jiahao0 on 2017/2/21.
 */
@Data
public class Disk {
    public static final String FILE_TYPE = "file";
    public static final String FOLDER_TYPE = "folder";

    private Integer id;
    private String name;
    private String sourceName;
    private Integer fid;
    private String size;
    private String createTime;
    private String createUser;
    private String type;

}
