package com.shw.crm.workbench.service;

import com.shw.crm.workbench.pojo.TranHistory;

import java.util.List;

public interface TranHistoryService {
    List<TranHistory> queryTranHistoryForDetailByTranId(String id);
}
