package com.shw.crm.workbench.mapper;

import com.shw.crm.workbench.pojo.Clue;

import java.util.List;
import java.util.Map;

public interface ClueMapper {
    int deleteByPrimaryKey(String id);

    int insert(Clue row);

    int insertSelective(Clue row);

    Clue selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Clue row);

    int updateByPrimaryKey(Clue row);

    /**
     * 通过id查询线索详情
     * @param id 线索id
     * @return 对应id的线索
     */
    Clue selectClueForDetailById(String id);

    /**
     * 新增线索
     * @param clue 新增的线索
     * @return 新增条数
     */
    int insertClue(Clue clue);

    int selectCountOfClueByCondition(Map<String,Object>map);

    List<Clue> selectClueByConditionForPage(Map<String,Object>map);

    /**
     * 通过id查询线索
     * @param id 线索id
     * @return 对应id的线索
     */
    Clue selectClueById(String id);

    int deleteClueByIds(String id);

    List<String> selectClueStageOfClueGroupByClueStage();

    List<Integer> selectCountOfClueGroupByClueStage();
}