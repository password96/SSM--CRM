package com.shw.crm.settings.mapper;

import com.shw.crm.settings.pojo.DicValue;

import java.util.List;

public interface DicValueMapper {
    int deleteByPrimaryKey(String id);

    int insert(DicValue row);

    int insertSelective(DicValue row);

    DicValue selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DicValue row);

    int updateByPrimaryKey(DicValue row);

    /**
     * 根据typeCode查询数据字典值
     * @param typeCode
     * @return
     */
    List<DicValue> selectDicValueByTypeCode(String typeCode);

    DicValue selectDicValueById(String id);
}