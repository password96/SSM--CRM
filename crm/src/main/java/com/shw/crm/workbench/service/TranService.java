package com.shw.crm.workbench.service;

import com.shw.crm.workbench.pojo.FunnelVO;
import com.shw.crm.workbench.pojo.Tran;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

public interface TranService {
    List<Tran> queryTransactionByConditionForPage(Map<String,Object> map);
    int queryCountOfTransactionByCondition(Map<String,Object> map);
    Tran queryTranForDetailById(String id);

    void saveCreateTransaction(Tran tran);

    Tran queryTransactionById(String id);

    void saveEditTransaction(Tran tran);

    void deleteTranByIds(String[] id);

    List<FunnelVO> queryCountOfTranGroupByStage();
}
