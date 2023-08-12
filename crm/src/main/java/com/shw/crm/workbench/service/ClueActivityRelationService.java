package com.shw.crm.workbench.service;

import com.shw.crm.workbench.pojo.ClueActivityRelation;

import java.util.List;

public interface ClueActivityRelationService {
    int saveCreateClueActivityRelationByList(List<ClueActivityRelation> clueActivityRelations);
    int deleteClueActivityRelationByClueIdAndActivityId(ClueActivityRelation clueActivityRelation);
}
