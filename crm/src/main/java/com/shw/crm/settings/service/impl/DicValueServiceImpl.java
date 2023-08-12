package com.shw.crm.settings.service.impl;

import com.shw.crm.settings.mapper.DicValueMapper;
import com.shw.crm.settings.pojo.DicValue;
import com.shw.crm.settings.service.DicValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("DicValueService")
public class DicValueServiceImpl implements DicValueService {

    @Autowired()
    private DicValueMapper dicValueMapper;

    @Override
    public List<DicValue> queryDicValueByTypeCode(String typeCode) {
        return dicValueMapper.selectDicValueByTypeCode(typeCode);
    }

    @Override
    public DicValue queryDicValueById(String id) {
        return dicValueMapper.selectDicValueById(id);
    }
}
