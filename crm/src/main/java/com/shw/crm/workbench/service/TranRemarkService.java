package com.shw.crm.workbench.service;

import com.shw.crm.workbench.pojo.TranRemark;
import java.util.List;

public interface TranRemarkService {
    List <TranRemark> queryTranRemarkForDetailByTranId(String id);
    int saveCreateTranRemark(TranRemark tranRemark);
    int deleteTranRemarkById(String id);
    int saveEditTranRemark(TranRemark tranRemark);
}
