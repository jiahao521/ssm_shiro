package com.kaishengit.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by jiahao0 on 2017/2/23.
 */
@Data
@AllArgsConstructor
public class WokersRentDetail implements Serializable {

    private Integer id;
    private String workersName;
    private Float workersPrice;
    private Integer num;
    private Float totalPrice;
    private Integer rentId;
}
