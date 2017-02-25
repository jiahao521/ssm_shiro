package com.kaishengit.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by jiahao0 on 2017/2/23.
 */
@Data
@AllArgsConstructor
public class Workers implements Serializable {
    private Integer id;
    private String name;
    private Integer totalNum;
    private Integer currNum;
    private float price;


}
