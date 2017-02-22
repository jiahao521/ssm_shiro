package com.kaishengit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by jiahao0 on 2017/2/20.
 */
@Data
@AllArgsConstructor
public class DataTablesResult {

    private String draw;
    private Long recordsLength;
    private Long recordsFilters;
    private Object data;

}
