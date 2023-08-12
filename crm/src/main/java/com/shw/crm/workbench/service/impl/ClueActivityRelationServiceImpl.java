package com.shw.crm.workbench.service.impl;

import com.shw.crm.workbench.mapper.ClueActivityRelationMapper;
import com.shw.crm.workbench.pojo.ClueActivityRelation;
import com.shw.crm.workbench.service.ClueActivityRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ClueActivityRelationService")
public class ClueActivityRelationServiceImpl implements ClueActivityRelationService {

    @Autowired
    private ClueActivityRelationMapper clueActivityRelationMapper;

    @Override
    public int saveCreateClueActivityRelationByList(List<ClueActivityRelation> clueActivityRelations) {
        return clueActivityRelationMapper.insertClueActivityRelationByList(clueActivityRelations);
    }

    @Override
    public int deleteClueActivityRelationByClueIdAndActivityId(ClueActivityRelation clueActivityRelation) {
        return clueActivityRelationMapper.deleteClueActivityRelationByClueIdAndActivityId(clueActivityRelation);
    }
}
