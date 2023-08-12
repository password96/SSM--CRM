package com.shw.crm.workbench.mapper;

import com.shw.crm.workbench.pojo.TranHistory;

import java.util.List;

public interface TranHistoryMapper {
    int deleteByPrimaryKey(String id);

    int insert(TranHistory row);

    int insertSelective(TranHistory row);

    TranHistory selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TranHistory row);

    int updateByPrimaryKey(TranHistory row);

    List<TranHistory> selectTranHistoryForDetailByTranId(String id);

    int insertTransactionHistory(TranHistory tranHistory);

    void deleteTranHistoryByTranIds(String[] ids);
}