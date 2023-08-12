package com.shw.crm.workbench.mapper;

import com.shw.crm.workbench.pojo.FunnelVO;
import com.shw.crm.workbench.pojo.Tran;

import java.util.List;
import java.util.Map;

public interface TranMapper {
    int deleteByPrimaryKey(String id);

    int insert(Tran row);

    int insertSelective(Tran row);

    Tran selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Tran row);

    int updateByPrimaryKey(Tran row);

    /**
     * 保存创建的交易
     * @param tran
     * @return
     */
    int insertTran(Tran tran);

    List<Tran> selectTransactionByConditionForPage(Map<String, Object> map);
    int selectCountOfTransactionByCondition(Map<String, Object> map);

    Tran selectTransactionById(String id);

    Tran selectTranForDetailById(String id);

    void updateTran(Tran tran);

    void deleteTranByIds(String[] ids);

    /**
     * 查询交易表中各个阶段的数据量
     * @return 数据集合
     */
    List<FunnelVO> selectCountOfTranGroupByStage();
}