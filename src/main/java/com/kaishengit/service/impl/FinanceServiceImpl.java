package com.kaishengit.service.impl;

import com.kaishengit.mapper.FinanceMapper;
import com.kaishengit.pojo.Finance;
import com.kaishengit.service.FinanceService;
import com.kaishengit.shiro.ShiroUtil;
import lombok.extern.slf4j.Slf4j;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by jiahao0 on 2017/2/23.
 */
@Service
public class FinanceServiceImpl implements FinanceService{

    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Autowired
    private FinanceMapper financeMapper;

    @Override
    public List<Finance> findFinanceByQueryParam(Map<String, Object> queryParam) {
        return financeMapper.findBySearchParam(queryParam);
    }

    @Override
    public Long countOfFinance() {
        return financeMapper.count();
    }

    @Override
    public Long filterCount(Map<String, Object> queryParam) {
        return financeMapper.filterCount(queryParam);
    }

    /**
     * 确认财务账单
     * @param id
     */
    @Override
    public void confirmById(Integer id) {

        Finance finance = financeMapper.findById(id);
        if(finance != null) {
            finance.setState(Finance.STATE_OK);
            finance.setConfirmDate(DateTime.now().toString("yyyy-mm-dd"));
            finance.setConfirmUser(ShiroUtil.getCurrentUserName());
            financeMapper.updateState(finance);
        }
    }
}

