package com.kaishengit.service;

import com.kaishengit.pojo.Finance;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by jiahao0 on 2017/2/23.
 */
@Service
public interface FinanceService {


    List<Finance> findFinanceByQueryParam(Map<String, Object> queryParam);

    Long countOfFinance();

    Long filterCount(Map<String, Object> queryParam);

    void confirmById(Integer id);
}
