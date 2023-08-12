package com.shw.crm.workbench.service.impl;

import com.shw.crm.workbench.mapper.TranHistoryMapper;
import com.shw.crm.workbench.pojo.TranHistory;
import com.shw.crm.workbench.service.TranHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("tranHistory")
public class TranHistoryImpl implements TranHistoryService {
    @Autowired
    private TranHistoryMapper tranHistoryMapper;

    @Override
    public List<TranHistory> queryTranHistoryForDetailByTranId(String id) {
        return tranHistoryMapper.selectTranHistoryForDetailByTranId(id);
    }
}
