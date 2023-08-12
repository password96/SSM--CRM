package com.shw.crm.workbench.mapper;

import com.shw.crm.workbench.pojo.ClueActivityRelation;

import java.util.List;

public interface ClueActivityRelationMapper {
    int deleteByPrimaryKey(String id);

    int insert(ClueActivityRelation row);

    int insertSelective(ClueActivityRelation row);

    ClueActivityRelation selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ClueActivityRelation row);

    int updateByPrimaryKey(ClueActivityRelation row);

    int insertClueActivityRelationByList(List<ClueActivityRelation> clueActivityRelations);

    int deleteClueActivityRelationByClueIdAndActivityId(ClueActivityRelation clueActivityRelation);

    /**
     * 根据clueID查询市场和线索关联的关系
     * @param id
     * @return
     */
    List<ClueActivityRelation> selectClueActivityRelationByClueId(String id);
    int deleteClueActivityRelationByClueIds(String id);
}