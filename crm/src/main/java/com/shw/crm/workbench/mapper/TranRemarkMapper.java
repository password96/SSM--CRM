package com.shw.crm.workbench.mapper;

import com.shw.crm.workbench.pojo.TranHistory;
import com.shw.crm.workbench.pojo.TranRemark;

import java.util.List;

public interface TranRemarkMapper {
    int deleteByPrimaryKey(String id);

    int insert(TranRemark row);

    int insertSelective(TranRemark row);

    TranRemark selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TranRemark row);

    int updateByPrimaryKey(TranRemark row);

    /**
     * 批量插入创建的交易线索备注的联系
     * @param list
     * @return
     */
    int insertTranRemarkByList(List<TranRemark> list);

    List <TranRemark> selectTranRemarkForDetailByTranId(String id);

    void deleteTranRemarkByTranIds(String[] ids);

    int insertTranRemark(TranRemark tranRemark);

    int deleteTranRemarkById(String id);

    int updateTranRemark(TranRemark tranRemark);
}