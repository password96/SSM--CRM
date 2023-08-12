package com.shw.crm.workbench.mapper;

import com.shw.crm.workbench.pojo.ClueRemark;

import java.util.List;

public interface ClueRemarkMapper {
    int deleteByPrimaryKey(String id);

    int insert(ClueRemark row);

    int insertSelective(ClueRemark row);

    ClueRemark selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ClueRemark row);

    int updateByPrimaryKey(ClueRemark row);

    /**
     * 根据线索id查询该线索的所有备注详细信息
     * @param clueId 线索id
     * @return 备注列表
     */
    List<ClueRemark> selectClueRemarkForDetailByClueId(String clueId);

    int insertClueRemark(ClueRemark clueRemark);

    List<ClueRemark> selectClueRemarkByClueId(String clueId);

    int deleteClueRemarkByClueId(String clueId);

    int deleteClueRemarkById(String id);

    int updateClueRemark(ClueRemark clueRemark);
}