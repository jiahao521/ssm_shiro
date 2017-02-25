package com.kaishengit.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by jiahao0 on 2017/2/23.
 */

@Data
public class Finance {

    public static final String TYPE_IN = "收入";
    public static final String TYPE_OUT = "支出";
    public static final String STATE_OK = "已确认";
    public static final String STATE_NEW = "未完成";

    private Integer id;
    private String serialNumber;
    private String type;
    private Float money;
    private String state;
    private String module;
    private String createDate;
    private String createUser;
    private String confirmUser;
    private String confirmDate;

    private String mark;
    private String moduleSerialNumber;


}
