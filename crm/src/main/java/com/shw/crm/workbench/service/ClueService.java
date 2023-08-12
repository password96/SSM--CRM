package com.shw.crm.workbench.service;

import com.shw.crm.workbench.pojo.Clue;

import java.util.List;
import java.util.Map;

public interface ClueService {
    int saveCreateClue(Clue clue);

    Clue queryClueForDetailById(String id);

    List<Clue> queryClueByConditionForPage(Map<String, Object> map);
    int queryCountOfClueByCondition(Map<String, Object> map);

    Clue queryClueById(String id);

    void saveConvertClue(Map<String, Object> map);

    List<String> queryClueStageOfClueGroupByClueStage();

    List<Integer> queryCountOfClueGroupByClueStage();
}
