package com.kaishengit.mapper;

import com.kaishengit.pojo.Finance;

import java.util.List;
import java.util.Map;

/**
 * Created by jiahao0 on 2017/2/23.
 */
public interface FinanceMapper {


    List<Finance> findBySearchParam(Map<String, Object> queryParam);

    Long count();

    Finance findById(Integer id);

    void updateState(Finance finance);

    Long filterCount(Map<String, Object> queryParam);

    void save(Finance finance);
}
