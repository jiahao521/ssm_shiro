package com.kaishengit.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by jiahao0 on 2017/2/23.
 */
@Data
@AllArgsConstructor
public class WokersRent implements Serializable {
    private Integer id;
    private String companyName;
    private String linkMan;
    private String cardNum;
    private String tel;
    private String address;
    private String fax;
    private String rentDate;
    private String backDate;
    private Integer totalDay;
    private Float totalPrice;
    private Float preCost;
    private Float lastCost;
    private Timestamp createTime;
    private String createUser;
    private String serialNumber;
    private String state;

}
