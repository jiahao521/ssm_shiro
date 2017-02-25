package com.kaishengit.controller;

import com.google.common.collect.Maps;
import com.kaishengit.dto.AjaxResult;
import com.kaishengit.dto.DataTablesResult;
import com.kaishengit.pojo.Finance;
import com.kaishengit.service.FinanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by jiahao0 on 2017/2/23.
 */
@Controller
@RequestMapping("/finance")
public class FinanceController {

    @Autowired
    private FinanceService financeService;

    @GetMapping("/day")
    public String dayist() {

      return "finance/day";
    }

    /**
     * 日报
     * @param request
     * @return
     */
    @GetMapping("/day/load")
    @ResponseBody
    public DataTablesResult load(HttpServletRequest request) {
        String draw = request.getParameter("draw");
        String start = request.getParameter("start");
        String length = request.getParameter("length");
        String day = request.getParameter("day");


        Map<String,Object> queryParam = Maps.newHashMap();
        queryParam.put("start",start);
        queryParam.put("length",length);
        queryParam.put("day",day);

        List<Finance> financeList = financeService.findFinanceByQueryParam(queryParam);
        Long count = financeService.countOfFinance();

        Long filterCount = financeService.filterCount(queryParam);

        return new DataTablesResult(draw,count,filterCount,financeList);

    }

    /**
     * 确认财务流水
     * @param id
     * @return
     */
    @PostMapping("/confirm/{id:\\d+}")
    @ResponseBody
    public AjaxResult confirmFinance(@PathVariable Integer id) {

        financeService.confirmById(id);
        return new AjaxResult(AjaxResult.SUCCESS);
    }



}
