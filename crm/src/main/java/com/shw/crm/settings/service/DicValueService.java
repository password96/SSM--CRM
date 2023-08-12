package com.shw.crm.settings.service;

import com.shw.crm.settings.pojo.DicValue;

import java.util.List;

public interface DicValueService {
    List<DicValue> queryDicValueByTypeCode(String typeCode);

    DicValue queryDicValueById(String id);
}
